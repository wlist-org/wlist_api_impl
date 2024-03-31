package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.Stable;
import com.xuxiaocheng.wlist.api.common.StableModule;

/**
 * The core client.
 * Use {@link com.xuxiaocheng.wlist.api.core.Client#connect(String, int)} to create an instance.
 */
@Stable(since = "1.4.0", module = StableModule.Core)
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
