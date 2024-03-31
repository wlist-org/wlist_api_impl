package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.Stable;
import com.xuxiaocheng.wlist.api.common.StableModule;
import com.xuxiaocheng.wlist.api.impl.ClientStarter;
import io.netty.buffer.ByteBuf;

import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The core client.
 * Use {@link com.xuxiaocheng.wlist.api.core.Client#connect(String, int)} to create an instance.
 */
@Stable(since = "1.4.0", module = StableModule.Core)
public final class CoreClient implements AutoCloseable {
    private final ClientStarter.ClientImpl client;

    private CoreClient(final ClientStarter.ClientImpl client) {
        this.client = client;
    }

    public static CompletableFuture<CoreClient> open(final String host, final int port) {
        return CompletableFuture.supplyAsync(() -> new ClientStarter.ClientImpl(new InetSocketAddress(host, port)), Main.InternalEventLoopGroup)
                .thenCompose(client -> client.open().thenApply(ignored -> client))
                .whenComplete((client, throwable) -> { if (throwable != null) client.close(); })
                .thenApply(CoreClient::new);
    }

    /**
     * Check whether this client is available.
     * @return true if it is available to continue use.
     */
    public boolean isAvailable() {
        return this.client.isActive();
    }

    public CompletableFuture<Void> send(final ByteBuf msg) {
        return this.client.send(msg);
    }

    public CompletableFuture<ByteBuf> recv() {
        return this.client.recv();
    }

    public CompletableFuture<Optional<ByteBuf>> recvOptional() {
        return this.client.recvOptional();
    }

    @Override
    public void close() {
        this.client.close();
    }
}
