package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.options.Duplicate;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;
import io.jbock.util.Either;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The core file API.
 */
public enum File {;
    /**
     * List the files in directory.
     * @param client the core client.
     * @param token the core token.
     * @param directory the location of the directory. ({@code assert directory.isDirectory;})
     * @param options the options for the list operation.
     * @return a future, with the list result or the refresh token.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     */
    public static CompletableFuture<Either<FileListInformation, RefreshConfirmation>> list(final CoreClient client, final String token, final FileLocation directory, final ListFileOptions options) { return Main.future(); }

    /**
     * Get the file/directory information.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file/directory
     * @return a future, with the optional file/directory information.
     */
    public static CompletableFuture<Optional<FileInformation>> get(final CoreClient client, final String token, final FileLocation file) { return Main.future(); }

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
     * Check the file/directory name in valid.
     * @param client the core client.
     * @param token the core token.
     * @param storage the target storage.
     * @param name the file/directory name.
     * @see com.xuxiaocheng.wlist.api.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.NameTooLongException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.InvalidFilenameException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.IllegalSuffixException
     */
    public static CompletableFuture<Void> checkName(final CoreClient client, final String token, final String storage, final String name) { throw Main.stub(); }

    /**
     * Create a new empty directory.
     * @param client the core client.
     * @param token the core token.
     * @param parent the parent directory.
     * @param name the name of the new directory.
     * @param duplicate duplication policy of the new directory.
     * @return a future, with the information of the new directory.
     * @see com.xuxiaocheng.wlist.api.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.NameTooLongException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.InvalidFilenameException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static CompletableFuture<FileInformation> mkdir(final CoreClient client, final String token, final FileLocation parent, final String name, final Duplicate duplicate) { return Main.future(); }
}
