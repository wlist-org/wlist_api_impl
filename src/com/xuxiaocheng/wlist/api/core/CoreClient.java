package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.common.NetworkFuture;
import com.xuxiaocheng.wlist.api.common.exceptions.NetworkException;
import com.xuxiaocheng.wlist.api.impl.ClientStarter;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * The core client.
 * Use {@link com.xuxiaocheng.wlist.api.core.Client#connect(String, int)} to create an instance.
 */
public record CoreClient(ClientStarter.ClientImpl client) implements AutoCloseable {
    public static NetworkFuture<CoreClient> open(final String host, final int port) {
        final NetworkFuture<CoreClient> future = NetworkFuture.create();
        future.completeAsync(() -> {
            final ClientStarter.ClientImpl client = new ClientStarter.ClientImpl(new InetSocketAddress(host,port));
            boolean flag = true;
            try {
                client.open();
                flag = false;
            } catch (final IOException exception) {
                throw new NetworkException(exception.getMessage());
            } finally {
                if (flag)
                    client.close();
            }
            return new CoreClient(client);
        });
        return future;
    }

    /**
     * Check whether this client is available.
     * @return true if it is available to continue use.
     */
    public boolean isAvailable() {
        return this.client.isActive();
    }

    @Override
    public void close() {
        this.client.close();
    }
}
