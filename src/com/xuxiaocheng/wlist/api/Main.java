package com.xuxiaocheng.wlist.api;

import java.util.concurrent.CompletableFuture;

public final class Main {
    private Main() {
        super();
    }

    public static <T> CompletableFuture<T> future() {
        final CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Stub!"));
        return future;
    }
}
