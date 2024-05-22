package com.xuxiaocheng.wlist.api.core.storages.types;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.storages.configs.LanzouConfig;

import java.util.concurrent.CompletableFuture;

/**
 * The core lanzou storage API.
 */
public enum Lanzou implements StorageType<LanzouConfig> {
    /** The instance. */ Instance;

    @Override
    public CompletableFuture<Long> add(final CoreClient client, final String token, final String storage, final LanzouConfig config) { return Main.future(); }

    @Override
    public CompletableFuture<Void> update(final CoreClient client, final String token, final long storage, final LanzouConfig config) { return Main.future(); }

    @Override
    public CompletableFuture<Void> checkConfig(final CoreClient client, final String token, final LanzouConfig config) { return Main.future(); }

    @Override
    public boolean isPrivate() {
        return true;
    }
}
