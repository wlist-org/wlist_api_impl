package com.xuxiaocheng.wlist.api;

import java.util.concurrent.CompletableFuture;

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
    public static <T> CompletableFuture<T> future() {
        final CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(Main.stub());
        return future;
    }
}
