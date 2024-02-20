package com.xuxiaocheng.wlist.api.core.broadcast;

import com.xuxiaocheng.wlist.api.core.CoreClient;

/**
 * Use {@link com.xuxiaocheng.wlist.api.core.broadcast.Broadcast#receiver(com.xuxiaocheng.wlist.api.core.CoreClient, String)} to create an instance.
 * @param client internal client.
 * @param token internal token.
 */
public record BroadcastClient(CoreClient client, String token) implements AutoCloseable {
    /**
     * Check whether this client is available.
     * @return true if it is available to continue use.
     */
    public boolean isAvailable() {
        return this.client.isAvailable();
    }

    @Override
    public void close() {
        this.client.close();
    }
}
