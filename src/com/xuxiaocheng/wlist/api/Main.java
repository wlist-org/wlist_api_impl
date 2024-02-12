package com.xuxiaocheng.wlist.api;

import java.util.concurrent.CompletableFuture;

public final class Main {
    private Main() {
        super();
    }

    public static RuntimeException stub() {
        return new RuntimeException("Stub!");
    }

    public static <T> CompletableFuture<T> future() {
        final CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(Main.stub());
        return future;
    }
}
