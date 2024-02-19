package com.xuxiaocheng.wlist.api.core.storages;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.NetworkFuture;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.storages.information.StorageInformation;
import com.xuxiaocheng.wlist.api.core.storages.information.StorageListInformation;
import com.xuxiaocheng.wlist.api.core.storages.options.ListStorageOptions;

import java.util.Optional;

/**
 * The core storage API.
 */
public enum Storage {;
    /**
     * List the storages.
     * @param client the core client.
     * @param token the core token.
     * @param options the options for the list operation.
     * @return a future, with the list result.
     */
    public static NetworkFuture<StorageListInformation> list(final CoreClient client, final String token, final ListStorageOptions options) { return Main.future(); }

    /**
     * Get the information of a storage.
     * @param client the core client.
     * @param token the core token.
     * @param storage the id of the storage.
     * @return a future, with the optional information of the storage.
     */
    public static NetworkFuture<Optional<StorageInformation>> get(final CoreClient client, final String token, final long storage) { return Main.future(); }

    /**
     * Remove a storage.
     * @param client the core client.
     * @param token the core token.
     * @param storage the id of the storage to remove.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageInLockException
     */
    public static NetworkFuture<Void> remove(final CoreClient client, final String token, final long storage) { return Main.future(); }
}
