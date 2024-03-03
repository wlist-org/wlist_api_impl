package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.NetworkFuture;
import com.xuxiaocheng.wlist.api.common.either.Either;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.information.FileDetailsInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.options.Duplicate;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;

import java.util.Optional;

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
    public static NetworkFuture<Either<FileListInformation, RefreshConfirmation>> list(final CoreClient client, final String token, final FileLocation directory, final ListFileOptions options) { return Main.future(); }

    /**
     * Get the file/directory information.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file/directory.
     * @param check true indicates the server should refresh the file information.
     * @return a future, with the optional file/directory information.
     */
    public static NetworkFuture<Optional<FileDetailsInformation>> get(final CoreClient client, final String token, final FileLocation file, final boolean check) { return Main.future(); }

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
    public static NetworkFuture<Void> trash(final CoreClient client, final String token, final FileLocation file) { return Main.future(); }

    /**
     * Check the file/directory name is valid.
     * Note that this method only provides fast filtering, and some cases may not be covered.
     * @param client the core client.
     * @param token the core token.
     * @param storage the target storage.
     * @param name the file/directory name.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.NameTooLongException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.InvalidFilenameException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.IllegalSuffixException
     * @return a future, normal completion means the name may be valid.
     */
    public static NetworkFuture<Void> checkName(final CoreClient client, final String token, final String storage, final String name) { throw Main.stub(); }

    /**
     * Create a new empty directory.
     * @param client the core client.
     * @param token the core token.
     * @param parent the parent directory.
     * @param name the name of the new directory.
     * @param duplicate duplication policy of the new directory.
     * @return a future, with the information of the new directory.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.NameTooLongException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.InvalidFilenameException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static NetworkFuture<FileInformation> mkdir(final CoreClient client, final String token, final FileLocation parent, final String name, final Duplicate duplicate) { return Main.future(); }

    /**
     * Copy the source file/directory to the target directory
     * @param client the core client.
     * @param token the core token.
     * @param source the source file/directory.
     * @param target the target directory. ({@code assert target.isDirectory();})
     * @param name the copied file/directory name.
     * @param duplicate duplication policy of the new file/directory.
     * @return a future, with the information of the new file/directory.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.ComplexOperationException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.SpaceNotEnoughException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.FlowNotEnoughException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.FileTooLargeException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.NameTooLongException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.InvalidFilenameException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.IllegalSuffixException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static NetworkFuture<FileInformation> copy(final CoreClient client, final String token, final FileLocation source, final FileLocation target, final String name, final Duplicate duplicate) { return Main.future(); }

    /**
     * Move the source file/directory to the target directory.
     * @param client the core client.
     * @param token the core token.
     * @param source the source file/directory.
     * @param target the target directory. ({@code assert target.isDirectory();})
     * @param duplicate duplication policy of the new file/directory.
     * @return a future, with the information of the new file/directory.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.ComplexOperationException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.SpaceNotEnoughException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.FlowNotEnoughException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.FileTooLargeException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.NameTooLongException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.InvalidFilenameException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static NetworkFuture<FileInformation> move(final CoreClient client, final String token, final FileLocation source, final FileLocation target, final Duplicate duplicate) { return Main.future(); }

    /**
     * Rename the source file/directory.
     * @param client the core client.
     * @param token the core token.
     * @param file the file/directory you want to rename.
     * @param name the new name.
     * @param duplicate duplication policy of the new file/directory.
     * @return a future, with the information of the new file/directory.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.ComplexOperationException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.NameTooLongException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.InvalidFilenameException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.IllegalSuffixException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static NetworkFuture<FileInformation> rename(final CoreClient client, final String token, final FileLocation file, final String name, final Duplicate duplicate) { return Main.future(); }
}
