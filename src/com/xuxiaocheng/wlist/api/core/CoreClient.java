package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.Main;

/**
 * The core client.
 * Use {@link com.xuxiaocheng.wlist.api.core.Client#connect(String, int)} to create an instance.
 */
public class CoreClient implements AutoCloseable {
    /**
     * The internal constructor.
     */
    private CoreClient() {
        super();
        throw Main.stub();
    }

    /**
     * Check whether this client is available.
     * @return true if it is available to continue use.
     */
    public boolean isAvailable() {
        return Client.isAvailable(this);
    }

    @Override
    public void close() {
        throw Main.stub();
    }
}
