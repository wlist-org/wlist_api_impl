package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.Main;

public class CoreClient implements AutoCloseable {
    private final long ptr;

    public CoreClient(final long ptr) {
        super();
        this.ptr = ptr;
    }

    @Override
    public void close() {
        throw Main.stub(); // TODO
    }

    @Override
    public String toString() {
        return "CoreClient{" +
                "ptr=" + this.ptr +
                '}';
    }
}
