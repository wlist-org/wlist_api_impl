package com.xuxiaocheng.wlist.api.core.storages;

import com.xuxiaocheng.wlist.api.common.Stable;
import com.xuxiaocheng.wlist.api.common.StableModule;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.storages.information.StorageDetailsInformation;
import com.xuxiaocheng.wlist.api.core.storages.information.StorageListInformation;
import com.xuxiaocheng.wlist.api.core.storages.options.ListStorageOptions;
import com.xuxiaocheng.wlist.api.impl.ClientStarter;
import com.xuxiaocheng.wlist.api.impl.enums.Functions;

import java.util.concurrent.CompletableFuture;

/**
 * The core storage API.
 */
@Stable(since = "1.11.0", module = StableModule.Core)
public enum Storage {;
    /**
     * List the storages.
     * @param client the core client.
     * @param token the core token.
     * @param options the options for the list operation.
     * @return a future, with the list result.
     */
    public static CompletableFuture<StorageListInformation> list(final CoreClient client, final String token, final ListStorageOptions options) {
        return ClientStarter.client(client, Functions.StorageList, packer -> {
            packer.packString(token);
            ListStorageOptions.serialize(options, packer);
        }, StorageListInformation::deserialize);
    }

    /**
     * Get the information of a storage.
     * @param client the core client.
     * @param token the core token.
     * @param storage the id of the storage.
     * @param check true indicates the server should recheck whether the storage is available.
     * @return a future, with the optional information of the storage.
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageNotFoundException
     */
    public static CompletableFuture<StorageDetailsInformation> get(final CoreClient client, final String token, final long storage, final boolean check) {
        return ClientStarter.client(client, Functions.StorageGet, packer -> packer.packString(token).packLong(storage).packBoolean(check), StorageDetailsInformation::deserialize);
    }

    /**
     * Remove a storage.
     * @param client the core client.
     * @param token the core token.
     * @param storage the id of the storage to remove.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageInLockException
     */
    public static CompletableFuture<Void> remove(final CoreClient client, final String token, final long storage) {
        return ClientStarter.client(client, Functions.StorageRemove, packer -> packer.packString(token).packLong(storage), ClientStarter::deserializeVoid);
    }

    /**
     * Rename a storage.
     * @param client the core client.
     * @param token the core token.
     * @param storage the id of the storage to rename.
     * @param name the new name of the storage.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.DuplicateStorageException
     */
    public static CompletableFuture<Void> rename(final CoreClient client, final String token, final long storage, final String name) {
        return ClientStarter.client(client, Functions.StorageRename, packer -> packer.packString(token).packLong(storage).packString(name), ClientStarter::deserializeVoid);
    }

    /**
     * Set a storage as readonly/writable.
     * For shared, if {@code readonly} is false, it will throw {@link com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageTypeMismatchedException}.
     * Note that if the storage is already readonly/writable, it will be normally completed with no-effort.
     * @param client the core client.
     * @param token the core token.
     * @param storage the id of the storage to set.
     * @param readonly true means set to readonly, false means set to writable.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageInLockException
     * @see com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageTypeMismatchedException
     */
    public static CompletableFuture<Void> setReadonly(final CoreClient client, final String token, final long storage, final boolean readonly) {
        return ClientStarter.client(client, Functions.StorageSetReadonly, packer -> packer.packString(token).packLong(storage).packBoolean(readonly), ClientStarter::deserializeVoid);
    }
}
