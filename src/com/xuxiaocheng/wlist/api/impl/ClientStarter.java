package com.xuxiaocheng.wlist.api.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
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

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class ClientStarter {
    private ClientStarter() {
        super();
    }

    private static void awaitFuture(final ChannelFuture future, final String message) throws IOException {
        try {
            future.await();
        } catch (final InterruptedException exception) {
            throw new IOException(message + exception);
        }
        final Throwable throwable = future.cause();
        if (throwable != null) {
            if (throwable instanceof final IOException exception)
                throw exception;
            throw new IOException(throwable);
        }
    }

    public static class ClientImpl implements Closeable {
        protected final EventLoopGroup clientEventLoop = new NioEventLoopGroup(1, new DefaultThreadFactory("ClientEventLoop"));
        protected final SocketAddress address;
        protected final BlockingQueue<ByteBuf> queue = new LinkedBlockingQueue<>();
        protected Channel channel;

        public ClientImpl(final SocketAddress address) {
            super();
            this.address = address;
        }

        public synchronized void open() throws IOException {
            if (this.channel != null) return;
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
            ClientStarter.awaitFuture(future, "Waiting for client to connect to addr: ");
            this.channel = future.channel();
        }

        public SocketAddress getAddress() {
            return this.address;
        }

        public synchronized boolean isActive() {
            return this.channel != null && this.channel.isActive();
        }

        public synchronized void send(final ByteBuf msg) throws IOException {
            if (!this.isActive()) throw new IOException("Client is not active.");
            final ChannelFuture future = this.channel.writeAndFlush(msg);
            ClientStarter.awaitFuture(future, "Waiting for client to send message: ");
        }

        public synchronized Optional<ByteBuf> recv() throws IOException {
            if (!this.isActive()) throw new IOException("Client is not active.");
            ByteBuf receive = null;
            while (receive == null && this.isActive()) {
                try {
                    receive = this.queue.poll(1, TimeUnit.SECONDS);
                } catch (final InterruptedException exception) {
                    throw new IOException("Waiting for client to recv message: " + exception);
                }
            }
            return Optional.ofNullable(receive);
        }

        @Override
        public synchronized void close() {
            if (this.channel == null) return;
            this.channel.close().addListener(f -> this.clientEventLoop.shutdownGracefully());
            while (true) {
                final ByteBuf deleted = this.queue.poll();
                if (deleted == null)
                    break;
                deleted.release();
            }
            //noinspection DataFlowIssue
            this.channel = null;
        }
    }


    private static native void exception(final ClientChannelHandler handler, final ChannelHandlerContext ctx, final String id, final Throwable throwable);

    public static class ClientChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
        private final ClientImpl client;

        private ClientChannelHandler(final ClientImpl client) {
            super();
            this.client = client;
        }

        @Override
        protected void channelRead0(final ChannelHandlerContext ctx, final ByteBuf msg) {
            this.client.queue.add(msg.retain());
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
