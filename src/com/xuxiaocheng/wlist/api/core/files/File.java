package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.common.Stable;
import com.xuxiaocheng.wlist.api.common.StableModule;
import com.xuxiaocheng.wlist.api.common.either.Either;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileDetailsInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.options.Duplicate;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;
import com.xuxiaocheng.wlist.api.impl.ClientStarter;
import com.xuxiaocheng.wlist.api.impl.enums.Functions;

import java.util.concurrent.CompletableFuture;

/**
 * The core file API.
 */
@Stable(since = "1.16.1", module = StableModule.Core)
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
    public static CompletableFuture<Either<FileListInformation, RefreshConfirmation>> list(final CoreClient client, final String token, final FileLocation directory, final ListFileOptions options) {
        return ClientStarter.client(client, Functions.FileList, packer -> {
            packer.packString(token);
            FileLocation.serialize(directory, packer);
            ListFileOptions.serialize(options, packer);
        }, unpacker -> unpacker.unpackBoolean() ? Either.left(FileListInformation.deserialize(unpacker)) : Either.right(RefreshConfirmation.deserialize(unpacker)));
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
    public static CompletableFuture<FileDetailsInformation> get(final CoreClient client, final String token, final FileLocation file, final boolean check) {
        return ClientStarter.client(client, Functions.FileGet, packer -> {
            packer.packString(token);
            FileLocation.serialize(file, packer);
            packer.packBoolean(check);
        }, FileDetailsInformation::deserialize);
    }

    /**
     * Check whether the file/directory name is valid.
     * Note that this method only provides fast filtering, and some cases may still not be covered.
     * This method may extend some cases from {@link com.xuxiaocheng.wlist.api.core.storages.types.StorageType#allowedSuffixes()}, etc.
     * <p>
     * The {@code DuplicateFileException} is the last exception to check.
     * This means if {@code DuplicateFileException} was thrown, the validation is passed.
     * @param client the core client.
     * @param token the core token.
     * @param name the file/directory name to check.
     * @param parent the parent directory location.
     * @param isDirectory whether is file/directory.
     * @return a future, normal completion means the name may be valid.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.DuplicateFileException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.NameTooLongException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.InvalidFilenameException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.IllegalSuffixException
     */
    public static CompletableFuture<Void> checkName(final CoreClient client, final String token, final String name, final FileLocation parent, final boolean isDirectory) {
        return ClientStarter.client(client, Functions.FileCheckName, packer -> {
            packer.packString(token);
            packer.packString(name);
            FileLocation.serialize(parent, packer);
            packer.packBoolean(isDirectory);
        }, ClientStarter::deserializeVoid);
    }

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
    public static CompletableFuture<FileInformation> mkdir(final CoreClient client, final String token, final FileLocation parent, final String name, final Duplicate duplicate) {
        return ClientStarter.client(client, Functions.FileMkdir, packer -> {
            packer.packString(token);
            FileLocation.serialize(parent, packer);
            packer.packString(name);
            packer.packString(duplicate.name());
        }, FileInformation::deserialize);
    }

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
    public static CompletableFuture<FileInformation> copy(final CoreClient client, final String token, final FileLocation source, final FileLocation target, final String name, final Duplicate duplicate) {
        return ClientStarter.client(client, Functions.FileCopy, packer -> {
            packer.packString(token);
            FileLocation.serialize(source, packer);
            FileLocation.serialize(target, packer);
            packer.packString(name);
            packer.packString(duplicate.name());
        }, FileInformation::deserialize);
    }

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
    public static CompletableFuture<FileInformation> move(final CoreClient client, final String token, final FileLocation source, final FileLocation target, final Duplicate duplicate) {
        return ClientStarter.client(client, Functions.FileMove, packer -> {
            packer.packString(token);
            FileLocation.serialize(source, packer);
            FileLocation.serialize(target, packer);
            packer.packString(duplicate.name());
        }, FileInformation::deserialize);
    }

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
    public static CompletableFuture<FileInformation> rename(final CoreClient client, final String token, final FileLocation file, final String name, final Duplicate duplicate) {
        return ClientStarter.client(client, Functions.FileRename, packer -> {
            packer.packString(token);
            FileLocation.serialize(file, packer);
            packer.packString(name);
            packer.packString(duplicate.name());
        }, FileInformation::deserialize);
    }
}
