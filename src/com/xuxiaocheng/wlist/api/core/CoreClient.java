package com.xuxiaocheng.wlist.api.core;

import java.util.concurrent.atomic.AtomicBoolean;

public class CoreClient implements AutoCloseable {
    public final long ptr;
    protected AtomicBoolean closed = new AtomicBoolean(false);

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
