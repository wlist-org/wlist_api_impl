package com.xuxiaocheng.wlist.api.core.broadcast;

import com.xuxiaocheng.wlist.api.core.CoreClient;

/**
 * Use {@link com.xuxiaocheng.wlist.api.core.broadcast.Broadcast#receiver(com.xuxiaocheng.wlist.api.core.CoreClient, String)} to create an instance.
 * <b>All parameters are internal.</b>
 */
public record BroadcastClient(CoreClient client, String token) implements AutoCloseable {
    @Override
    public void close() {
        this.client.close();
    }
}
