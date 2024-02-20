package com.xuxiaocheng.wlist.api.core;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The core client.
 * Use {@link com.xuxiaocheng.wlist.api.core.Client#connect(String, int)} to create an instance.
 */
public class CoreClient implements AutoCloseable {
    /**
     * An internal pointer.
     */
    protected final long ptr;

    /**
     * A flag indicates whether this client is closed.
     */
    protected final AtomicBoolean closed = new AtomicBoolean(false);

    /**
     * The internal constructor.
     * @param ptr internal pointer.
     */
    public CoreClient(final long ptr) {
        super();
        this.ptr = ptr;
    }

    /**
     * Check whether this client is available.
     * @return true if it is available to continue use.
     */
    public boolean isAvailable() {
        if (this.closed.get())
            return false;
        return Client.isAvailable(this);
    }

    @Override
    public void close() {
        if (this.closed.compareAndSet(false, true))
            Client.close(this);
    }

    @Override
    public String toString() {
        return "CoreClient{" +
                "ptr=" + this.ptr +
                ", closed=" + this.closed +
                '}';
    }
}
