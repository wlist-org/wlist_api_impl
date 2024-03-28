package com.xuxiaocheng.wlist.api.impl;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.exceptions.InternalException;
import com.xuxiaocheng.wlist.api.common.exceptions.NetworkException;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import com.xuxiaocheng.wlist.api.impl.enums.Functions;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

public final class ClientStarter {
    private ClientStarter() {
        super();
    }

    private static CompletableFuture<Void> awaitFuture(final ChannelFuture channelFuture, final String message) {
        final CompletableFuture<Void> future = new CompletableFuture<>();
        channelFuture.addListener(f -> {
            if (f.cause() == null) {
                future.complete(null);
                return;
            }
            final Throwable throwable = f.cause();
            if (throwable instanceof IOException)
                future.completeExceptionally(new NetworkException(message, throwable));
            else
                future.completeExceptionally(new InternalException(message, throwable));
        });
        return future;
    }

    public static class ClientImpl implements Closeable {
        protected final EventLoopGroup clientEventLoop = new NioEventLoopGroup(1, new DefaultThreadFactory("ClientEventLoop"));
        protected final SocketAddress address;
        protected final BlockingQueue<CompletableFuture<Optional<ByteBuf>>> receiver = new LinkedBlockingQueue<>();
        protected final BlockingQueue<ByteBuf> buffers = new LinkedBlockingQueue<>();
        protected transient Channel channel;

        public ClientImpl(final SocketAddress address) {
            super();
            this.address = address;
        }

        public synchronized CompletableFuture<Void> open() {
            if (this.channel != null)
                return CompletableFuture.completedFuture(null);
            if (this.clientEventLoop.isShuttingDown())
                throw new NetworkException("Closed client.");
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(this.clientEventLoop);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.SO_REUSEADDR, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(final SocketChannel ch) {
                    final ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("LengthDecoder", new LengthFieldBasedFrameDecoder(ClientStarter.getMaxPacketSize(), 0, 4, 0, 4));
                    pipeline.addLast("LengthEncoder", new LengthFieldPrepender(4));
                    pipeline.addLast("Cipher", new ClientCodec());
                    pipeline.addLast("ClientHandler", new ClientChannelHandler(ClientImpl.this));
                }
            });
            final ChannelFuture future = bootstrap.connect(this.address);
            this.channel = future.channel();
            return ClientStarter.awaitFuture(future, "Waiting for client to connect to addr.");
        }

        @SuppressWarnings("unused")
        public SocketAddress getAddress() {
            return this.address;
        }

        public synchronized boolean isActive() {
            return this.channel != null && this.channel.isActive();
        }

        // The returned future is completed synchronously.
        private synchronized CompletableFuture<Channel> getChanel() {
            if (!this.isActive())
                return CompletableFuture.failedFuture(new NetworkException("Client is not active."));
            return CompletableFuture.completedFuture(this.channel);
        }

        public synchronized CompletableFuture<Void> send(final ByteBuf msg) {
            return this.getChanel().thenCompose(channel -> {
                final ChannelFuture future = channel.writeAndFlush(msg);
                return ClientStarter.awaitFuture(future, "Waiting for client to send message.");
            });
        }

        public CompletableFuture<ByteBuf> recv() {
            return this.recvOptional().thenApply(buffer -> {
                if (buffer.isPresent())
                    return buffer.get();
                throw new NetworkException("Client is closing");
            });
        }

        public synchronized CompletableFuture<Optional<ByteBuf>> recvOptional() {
            return this.getChanel().thenCompose(ignored -> {
                final ByteBuf receive = this.buffers.poll();
                if (receive != null)
                    return CompletableFuture.completedFuture(Optional.of(receive));
                final CompletableFuture<Optional<ByteBuf>> future = new CompletableFuture<>();
                this.receiver.add(future);
                return future;
            });
        }

        public synchronized void fireRecv(final ByteBuf msg) {
            final CompletableFuture<Optional<ByteBuf>> receiver = this.receiver.poll();
            if (receiver != null)
                receiver.complete(Optional.of(msg));
            else
                this.buffers.add(msg);
        }

        @Override
        public synchronized void close() {
            if (this.channel == null) return;
            this.channel.close().addListener(f -> this.clientEventLoop.shutdownGracefully());
            //noinspection DataFlowIssue
            this.channel = null;
            while (true) {
                final ByteBuf deleted = this.buffers.poll();
                if (deleted == null) break;
                deleted.release();
            }
            while (true) {
                final CompletableFuture<Optional<ByteBuf>> deleted = this.receiver.poll();
                if (deleted == null) break;
                deleted.complete(Optional.empty());
            }
        }
    }


    private static native void exception(final ClientChannelHandler handler, final ChannelHandlerContext ctx, final String id, final Throwable throwable);

    public static class ClientChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
        private final ClientImpl client;

        private ClientChannelHandler(final ClientImpl client) {
            super(false);
            this.client = client;
        }

        @Override
        protected void channelRead0(final ChannelHandlerContext ctx, final ByteBuf msg) {
            this.client.fireRecv(msg);
        }

        @Override
        public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
            ClientStarter.exception(this, ctx, ctx.channel().id().asLongText(), cause);
            this.client.close();
        }
    }


    private static native int getMaxPacketSize();
    private static native void encode(final ClientCodec codec, final ChannelHandlerContext ctx, final String id, final ByteBuf msg, final List<Object> out);
    private static native void decode(final ClientCodec codec, final ChannelHandlerContext ctx, final String id, final ByteBuf msg, final List<Object> out);

    public static class ClientCodec extends MessageToMessageCodec<ByteBuf, ByteBuf> {
        @Override
        protected void encode(final ChannelHandlerContext ctx, final ByteBuf msg, final List<Object> out) {
            ClientStarter.encode(this, ctx, ctx.channel().id().asLongText(), msg, out);
        }

        @Override
        protected void decode(final ChannelHandlerContext ctx, final ByteBuf msg, final List<Object> out) {
            ClientStarter.decode(this, ctx, ctx.channel().id().asLongText(), msg, out);
        }
    }
}
