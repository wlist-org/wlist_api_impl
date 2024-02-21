package com.xuxiaocheng.wlist.api.common;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A wrapper for CompletableFuture for better management.
 * Note that the future will be complete in native thread,
 * so you cannot call sync method like {@code thenAccept} etc., use {@code thenAcceptAsync} instead.
 * @param <T> the result type.
 */
public final class NetworkFuture<T> extends CompletableFuture<T> implements Recyclable {
    /**
     * Create a new NetworkFuture.
     * @return the new instance.
     * @param <T> the result type.
     */
    public static <T> NetworkFuture<T> create() {
        return new NetworkFuture<>();
    }

    /**
     * Shut down all native threads.
     * <p>
     * This method can be called when exit the jvm.
     * If you don't call this method, these native threads will block the jvm exit process.
     * But to avoid panic, the futures created after calling will never be complete.
     */
    public static native void shutdownNativeThreads();

    private NetworkFuture() {
        super();
    }

    private final AtomicBoolean closed = new AtomicBoolean(false);

    @Override
    public void close() {
        if (!this.isDone())
            this.cancel(true);
        if (this.closed.compareAndSet(false, true))
            this.recycle();
    }

    @Override
    public String toString() {
        return super.toString() + (this.closed.get() ? "[closed]" : "[using]");
    }
}
