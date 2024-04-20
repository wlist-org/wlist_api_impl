package com.xuxiaocheng.wlist.api.core.trashes;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.either.Either;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;

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
    public static CompletableFuture<Either<FileListInformation, RefreshConfirmation>> listTrash(final CoreClient client, final String token, final ListFileOptions options) { return Main.future(); }

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
    public static CompletableFuture<Void> trash(final CoreClient client, final String token, final FileLocation file) { return Main.future(); }

    /**
     * Restore the file/directory.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file/directory to restore.
     * @return a future, with the restored file information.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileInLockException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static CompletableFuture<FileInformation> restore(final CoreClient client, final String token, final FileLocation file) { return Main.future(); }
}
