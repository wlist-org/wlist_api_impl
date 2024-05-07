package com.xuxiaocheng.wlist.api.core.trashes;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.either.Either;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashDetailsInformation;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashInformation;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashListInformation;
import com.xuxiaocheng.wlist.api.core.trashes.options.ListTrashOptions;

import java.util.concurrent.CompletableFuture;

/**
 * The core trash API.
 */
public enum Trash {;
    /**
     * List the files in trash.
     * @param client the core client.
     * @param token the core token.
     * @param options the options for the list operation.
     * @return a future, with the list result or the refresh token.
     */
    public static CompletableFuture<Either<TrashListInformation, RefreshConfirmation>> list(final CoreClient client, final String token, final ListTrashOptions options) { return Main.future(); }

    /**
     * Refresh the trash.
     * @param client the core client.
     * @param token the core token.
     * @return a future, with the refresh confirmation.
     */
    public static CompletableFuture<RefreshConfirmation> refresh(final CoreClient client, final String token) { return Main.future(); }

    /**
     * Get the file/directory information.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file/directory.
     * @param check true indicates the server should refresh the file information.
     * @return a future, with the file/directory information.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     */
    public static CompletableFuture<TrashDetailsInformation> get(final CoreClient client, final String token, final FileLocation file, final boolean check) { return Main.future(); }

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
    public static CompletableFuture<TrashInformation> trash(final CoreClient client, final String token, final FileLocation file) { return Main.future(); }

    /**
     * Restore the trashed file/directory.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file/directory to restore.
     * @param parent the parent location the file/directory restored to.
     * @return a future, with the restored file information.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileInLockException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static CompletableFuture<FileInformation> restore(final CoreClient client, final String token, final FileLocation file, final FileLocation parent) { return Main.future(); }

    /**
     * Delete the trashed file/directory.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the trashed file/directory to delete.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static CompletableFuture<Void> delete(final CoreClient client, final String token, final FileLocation file) { return Main.future(); }

    /**
     * Delete all the trashed files and directories.
     * @param client the core client.
     * @param token the core token.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static CompletableFuture<Void> deleteAll(final CoreClient client, final String token) { return Main.future(); }
}
