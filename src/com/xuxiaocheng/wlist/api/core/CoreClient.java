package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.exceptions.NetworkException;
import com.xuxiaocheng.wlist.api.impl.ClientStarter;
import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The core client.
 * Use {@link com.xuxiaocheng.wlist.api.core.Client#connect(String, int)} to create an instance.
 */
public final class CoreClient implements AutoCloseable {
    private final ClientStarter.ClientImpl client;

    private CoreClient(final ClientStarter.ClientImpl client) {
        this.client = client;
    }

    public static CompletableFuture<CoreClient> open(final String host, final int port) {
        return CompletableFuture.supplyAsync(() -> {
            final ClientStarter.ClientImpl client = new ClientStarter.ClientImpl(new InetSocketAddress(host, port));
            boolean flag = true;
            try {
                client.open();
                flag = false;
            } catch (final IOException exception) {
                throw new NetworkException(exception.getLocalizedMessage());
            } finally {
                if (flag)
                    client.close();
            }
            return new CoreClient(client);
        }, Main.InternalEventLoopGroup);
    }

    /**
     * Check whether this client is available.
     * @return true if it is available to continue use.
     */
    public boolean isAvailable() {
        return this.client.isActive();
    }

    public void send(final ByteBuf msg) {
        try {
            this.client.send(msg);
        } catch (final IOException exception) {
            throw new NetworkException(exception.getLocalizedMessage());
        }
    }

    public Optional<ByteBuf> recv() {
        try {
            return this.client.recv();
        } catch (final IOException exception) {
            throw new NetworkException(exception.getLocalizedMessage());
        }
    }

    @Override
    public void close() {
        this.client.close();
    }
}
