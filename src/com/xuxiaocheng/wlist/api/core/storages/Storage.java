package com.xuxiaocheng.wlist.api.core.storages;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.storages.configs.LanzouConfig;

import java.util.concurrent.CompletableFuture;

/**
 * The core storage API.
 */
public enum Storage {;
    /**
     * Remove a storage.
     * @param client the core client.
     * @param token the core token.
     * @param storage the name of the storage to remove.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageInLockException
     */
    public static CompletableFuture<Void> remove(final CoreClient client, final String token, final String storage) { return Main.future(); }

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
    public static CompletableFuture<Void> addLanzou(final CoreClient client, final String token, final String storage, final LanzouConfig config) { return Main.future(); }
}
