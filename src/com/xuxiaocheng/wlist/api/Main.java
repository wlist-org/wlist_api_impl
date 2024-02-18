package com.xuxiaocheng.wlist.api;

import com.xuxiaocheng.wlist.api.common.NetworkFuture;

/**
 * Stub function.
 */
public final class Main {
    private Main() {
        super();
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
    public static <T> NetworkFuture<T> future() {
        final NetworkFuture<T> future = NetworkFuture.create();
        future.completeExceptionally(Main.stub());
        return future;
    }
}
