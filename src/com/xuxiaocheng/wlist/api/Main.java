package com.xuxiaocheng.wlist.api;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.CompletableFuture;

/**
 * The common global API.
 */
public enum Main {;
    public static final EventExecutorGroup InternalEventLoopGroup = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors() << 2, new DefaultThreadFactory("InternalExecutors"));

    /**
     * Initialize the core server.
     * Note that you **must** call this method before calling all the others.
     * You should ensure these directories exist and have permissions to read/write them.
     * The path should be absolute.
     * @param cache the directory of cache.
     * @param data the directory of data.
     */
    public static native void initialize(final String cache, final String data);

    private static native void shutdownNativeThreads();

    /**
     * Shut down all internal threads.
     * <p>
     * This method can be called when exit the jvm.
     * If you don't call this method, these threads will block the jvm exit process.
     * If you called any other method after calling this, the behavior of these methods is undefined.
     */
    public static void shutdownThreads() {
        Main.shutdownNativeThreads();
        Main.InternalEventLoopGroup.shutdownGracefully().awaitUninterruptibly();
    }


    /**
     * Stub exception.
     * @return the stub exception.
     */
    public static RuntimeException stub() {
        return new RuntimeException("Stub!");
    }

    /**
     * Stub future.
     * @param <T> the generic type.
     * @return the stub future.
     */
    public static <T> CompletableFuture<T> future() {
        return CompletableFuture.failedFuture(Main.stub());
    }
}
