package com.xuxiaocheng.wlist.api.core.trashes;

import com.xuxiaocheng.wlist.api.common.Stable;
import com.xuxiaocheng.wlist.api.common.StableModule;
import com.xuxiaocheng.wlist.api.common.either.Either;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashDetailsInformation;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashInformation;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashListInformation;
import com.xuxiaocheng.wlist.api.core.trashes.options.ListTrashOptions;
import com.xuxiaocheng.wlist.api.impl.ClientStarter;
import com.xuxiaocheng.wlist.api.impl.enums.Functions;

import java.util.concurrent.CompletableFuture;

/**
 * The core trash API.
 */
@Stable(since = "1.20.0", module = StableModule.Core)
public enum Trash {;
    /**
     * List the files in trash.
     * @param client the core client.
     * @param token the core token.
     * @param storage the id of the storage.
     * @param options the options for the list operation.
     * @return a future, with the list result or the refresh token.
     */
    public static CompletableFuture<Either<TrashListInformation, RefreshConfirmation>> list(final CoreClient client, final String token, final long storage, final ListTrashOptions options) {
        return ClientStarter.client(client, Functions.TrashList, packer -> {
            packer.packString(token);
            packer.packLong(storage);
            ListTrashOptions.serialize(options, packer);
        }, unpacker -> unpacker.unpackBoolean() ? Either.left(TrashListInformation.deserialize(unpacker)) : Either.right(RefreshConfirmation.deserialize(unpacker)));
    }

    /**
     * Refresh the trash.
     * @param client the core client.
     * @param token the core token.
     * @param storage the id of the storage.
     * @return a future, with the refresh confirmation.
     */
    public static CompletableFuture<RefreshConfirmation> refresh(final CoreClient client, final String token, final long storage) {
        return ClientStarter.client(client, Functions.TrashRefresh, packer -> packer.packString(token).packLong(storage), RefreshConfirmation::deserialize);
    }

    /**
     * Get the file/directory information.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file/directory.
     * @param check true indicates the server should refresh the file information.
     * @return a future, with the file/directory information.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     */
    public static CompletableFuture<TrashDetailsInformation> get(final CoreClient client, final String token, final FileLocation file, final boolean check) {
        return ClientStarter.client(client, Functions.TrashGet, packer -> {
            packer.packString(token);
            FileLocation.serialize(file, packer);
            packer.packBoolean(check);
        }, TrashDetailsInformation::deserialize);
    }

    /**
     * Trash the file/directory.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file/directory
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.ComplexOperationException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileInLockException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static CompletableFuture<TrashInformation> trash(final CoreClient client, final String token, final FileLocation file) {
        return ClientStarter.client(client, Functions.TrashTrash, packer -> {
            packer.packString(token);
            FileLocation.serialize(file, packer);
        }, TrashInformation::deserialize);
    }

    /**
     * Restore the trashed file/directory.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file/directory to restore.
     * @param parent the parent directory id the file/directory restored to.
     * @return a future, with the restored file information.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileInLockException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static CompletableFuture<FileInformation> restore(final CoreClient client, final String token, final FileLocation file, final long parent) {
        return ClientStarter.client(client, Functions.TrashRestore, packer -> {
            packer.packString(token);
            FileLocation.serialize(file, packer);
            packer.packLong(parent);
        }, FileInformation::deserialize);
    }

    /**
     * Delete the trashed file/directory.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the trashed file/directory to delete.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static CompletableFuture<Void> delete(final CoreClient client, final String token, final FileLocation file) {
        return ClientStarter.client(client, Functions.TrashDelete, packer -> {
            packer.packString(token);
            FileLocation.serialize(file, packer);
        }, ClientStarter::deserializeVoid);
    }

    /**
     * Delete all the trashed files and directories.
     * @param client the core client.
     * @param token the core token.
     * @param storage the id of the storage.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static CompletableFuture<Void> deleteAll(final CoreClient client, final String token, final long storage) {
        return ClientStarter.client(client, Functions.TrashDeleteAll, packer -> packer.packString(token).packLong(storage), ClientStarter::deserializeVoid);
    }
}
