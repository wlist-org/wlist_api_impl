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
    public final long ptr;

    /**
     * A flag indicates whether this client is closed.
     */
    protected AtomicBoolean closed = new AtomicBoolean(false);

    /**
     * The internal constructor.
     * @param ptr internal pointer.
     */
    public CoreClient(final long ptr) {
        super();
        this.ptr = ptr;
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
