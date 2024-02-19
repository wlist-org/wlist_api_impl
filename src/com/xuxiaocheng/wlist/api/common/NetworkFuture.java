package com.xuxiaocheng.wlist.api.common;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A wrapper for CompletableFuture for better management.
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
