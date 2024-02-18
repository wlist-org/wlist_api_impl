package com.xuxiaocheng.wlist.api.common;

import java.util.concurrent.CompletableFuture;

/**
 * A wrapper for CompletableFuture for better management.
 * @param <T> the result type.
 */
public class NetworkFuture<T> extends CompletableFuture<T> implements AutoCloseable {
    @Override
    public void close() {
    }
}
