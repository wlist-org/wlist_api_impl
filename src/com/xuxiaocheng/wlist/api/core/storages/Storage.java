package com.xuxiaocheng.wlist.api.core.storages;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.storages.information.StorageInformation;

import java.util.concurrent.CompletableFuture;

/**
 * The core storage API.
 */
public enum Storage {;
    /**
     * Get the information of a storage.
     * @param client the core client.
     * @param token the core token.
     * @param storage the name of the storage.
     * @return a future, with the information of the storage.
     */
    public static CompletableFuture<StorageInformation> get(final CoreClient client, final String token, final String storage) { return Main.future(); }

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
}
