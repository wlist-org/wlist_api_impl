package com.xuxiaocheng.wlist.api.impl;

import com.xuxiaocheng.wlist.api.common.exceptions.InternalException;
import com.xuxiaocheng.wlist.api.common.exceptions.NetworkException;
import com.xuxiaocheng.wlist.api.core.exceptions.MultiInstanceException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class ServerStarter {
    private ServerStarter() {
        super();
    }

    public static final EventExecutorGroup CodecExecutors = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors() << 2, new DefaultThreadFactory("CodecExecutors"));
    public static final EventExecutorGroup ServerExecutors = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors() << 1, new DefaultThreadFactory("ServerExecutors"));

    /**
     * 0: Stopped,
     * 1: Starting,
     * 2: Started,
     * 3: Stopping,
     */
    private static final AtomicInteger started = new AtomicInteger(0);
    private static final EventExecutorGroup bossGroup = new NioEventLoopGroup(Math.max(1, Runtime.getRuntime().availableProcessors() >>> 1));
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() << 1);

    public static int start(final int defaultPort) {
        if (!ServerStarter.started.compareAndSet(0, 1)) throw new MultiInstanceException();
        try {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(ServerStarter.workerGroup, ServerStarter.workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
            serverBootstrap.option(ChannelOption.SO_REUSEADDR, true);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(final SocketChannel ch) {
                    final ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(ServerStarter.CodecExecutors, "ClosedController", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(final ChannelHandlerContext ctx) throws Exception {
                            if (ServerStarter.started.get() != 2)
                                ctx.close();
                            else
                                super.channelActive(ctx);
                        }
                    });
                    pipeline.addLast(ServerStarter.CodecExecutors, "LengthDecoder", new LengthFieldBasedFrameDecoder(ServerStarter.getMaxPacketSize(), 0, 4, 0, 4));
                    pipeline.addLast(ServerStarter.CodecExecutors, "LengthEncoder", new LengthFieldPrepender(4));
                    pipeline.addLast(ServerStarter.CodecExecutors, "Cipher", new ServerCodec());
                    pipeline.addLast(ServerStarter.ServerExecutors, "ServerHandler", ServerStarter.handlerInstance);
                }
            });
            ChannelFuture future = null;
            try {
                boolean flag = false;
                if (defaultPort != 0) {
                    future = serverBootstrap.bind(defaultPort).await();
                    if (future.cause() != null)
                        flag = true;
                }
                if (defaultPort == 0 || flag) {
                    future = serverBootstrap.bind(0).await();
                    if (future.cause() != null)
                        throw new NetworkException(future.cause().getMessage());
                }
            } catch (final InterruptedException exception) {
                throw new InternalException("Waiting for server to bind on port.");
            }
            final InetSocketAddress address = (InetSocketAddress) future.channel().localAddress();
            ServerStarter.started.set(2);
            return address.getPort();
        } finally {
            ServerStarter.started.compareAndSet(1, 0);
        }
    }

    public static void stop() {
        if (!ServerStarter.started.compareAndSet(2, 3)) return;
        try {
            final Future<?>[] futures = new Future<?>[2];
            futures[0] = ServerStarter.bossGroup.shutdownGracefully();
            futures[1] = ServerStarter.workerGroup.shutdownGracefully();
            for (final Future<?> future: futures)
                future.syncUninterruptibly();
        } catch (final Throwable throwable) {
            throw new InternalException("Stopping server. " + throwable);
        } finally {
            ServerStarter.started.set(0);
        }
    }


    private static native void active(final ServerChannelHandler handler, final ChannelHandlerContext ctx, final String id);
    private static native void inactive(final ServerChannelHandler handler, final ChannelHandlerContext ctx, final String id);
    private static native void exception(final ServerChannelHandler handler, final ChannelHandlerContext ctx, final String id, final Throwable throwable);

    private static final ChannelHandler handlerInstance = new ServerChannelHandler();
    @ChannelHandler.Sharable
    public static class ServerChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        public void channelActive(final ChannelHandlerContext ctx) {
            ServerStarter.active(this, ctx, ctx.channel().id().asLongText());
        }

        @Override
        public void channelInactive(final ChannelHandlerContext ctx) {
            ServerStarter.inactive(this, ctx, ctx.channel().id().asLongText());
        }

        @Override
        protected void channelRead0(final ChannelHandlerContext ctx, final ByteBuf msg) {
//            @SuppressWarnings("resource") final NetworkFuture<ByteBuf> future = ServerStarter.handle(ctx.channel().id().asLongText(), msg);
//            future.thenAcceptAsync(buffer -> ctx.channel().writeAndFlush(buffer), ServerStarter.ServerExecutors).thenRun(future::close);
            // TODO: deserialize in java side.
        }

        @Override
        public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
            ctx.close();
            if (cause instanceof CodecException || (cause instanceof SocketException && !ctx.channel().isActive()))
                return;
            ServerStarter.exception(this, ctx, ctx.channel().id().asLongText(), cause);
        }
    }


    private static native int getMaxPacketSize();
    private static native void encode(final ServerCodec codec, final ChannelHandlerContext ctx, final String id, final ByteBuf msg, final List<Object> out);
    private static native void decode(final ServerCodec codec, final ChannelHandlerContext ctx, final String id, final ByteBuf msg, final List<Object> out);

    public static class ServerCodec extends MessageToMessageCodec<ByteBuf, ByteBuf> {
        @Override
        protected void encode(final ChannelHandlerContext ctx, final ByteBuf msg, final List<Object> out) {
            ServerStarter.encode(this, ctx, ctx.channel().id().asLongText(), msg, out);
        }

        @Override
        protected void decode(final ChannelHandlerContext ctx, final ByteBuf msg, final List<Object> out) {
            ServerStarter.decode(this, ctx, ctx.channel().id().asLongText(), msg, out);
        }
    }
}
