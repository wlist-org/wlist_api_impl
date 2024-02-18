package com.xuxiaocheng.wlist.api.core.storages.types;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.NetworkFuture;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.storages.configs.LanzouConfig;

/**
 * The core lanzou storage API.
 */
public enum Lanzou implements StorageType {/** The instance. */ Instance;

    /**
     * Add a lanzou storage.
     * @param client the core client.
     * @param token the core token.
     * @param storage the name of the storage to add.
     * @param config the Lanzou configuration.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageExistsException
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.IncorrectStorageAccountException
     * @see com.xuxiaocheng.wlist.api.exceptions.TooLargeDataException
     */
    public static NetworkFuture<Void> add(final CoreClient client, final String token, final String storage, final LanzouConfig config) { return Main.future(); }
}
