package com.xuxiaocheng.wlist.api.impl.enums;

import com.xuxiaocheng.wlist.api.common.exceptions.IncorrectArgumentException;
import com.xuxiaocheng.wlist.api.common.exceptions.MatchFrequencyControlException;
import com.xuxiaocheng.wlist.api.common.exceptions.NetworkException;
import com.xuxiaocheng.wlist.api.common.exceptions.PasswordNotMatchedException;
import com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException;
import com.xuxiaocheng.wlist.api.common.exceptions.TooLargeDataException;
import com.xuxiaocheng.wlist.api.common.exceptions.UnavailableApiVersionException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.ComplexOperationException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.DuplicateFileException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.FileInLockException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.UploadChunkIncompleteException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.FileTooLargeException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.FlowNotEnoughException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.IllegalSuffixException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.InvalidFilenameException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.NameTooLongException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException;
import com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.SpaceNotEnoughException;
import com.xuxiaocheng.wlist.api.core.storages.exceptions.DuplicateStorageException;
import com.xuxiaocheng.wlist.api.core.storages.exceptions.IncorrectStorageAccountException;
import com.xuxiaocheng.wlist.api.core.storages.exceptions.InvalidStorageConfigException;
import com.xuxiaocheng.wlist.api.core.storages.exceptions.MismatchStorageTypeException;
import com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageInLockException;
import com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageNotFoundException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;

public enum Exceptions {
    Internal(unpacker -> new NetworkException("Server error.")),

    UnavailableApiVersion(UnavailableApiVersionException::deserialize),
    MatchFrequencyControl(MatchFrequencyControlException::deserialize),
    IncorrectArgument(IncorrectArgumentException::deserialize),
    TooLargeData(TooLargeDataException::deserialize),
    PasswordNotMatched(PasswordNotMatchedException::deserialize),
    TokenExpired(TokenExpiredException::deserialize),

    InvalidStorageConfig(InvalidStorageConfigException::deserialize),
    IncorrectStorageAccount(IncorrectStorageAccountException::deserialize),
    DuplicateStorage(DuplicateStorageException::deserialize),
    StorageNotFound(StorageNotFoundException::deserialize),
    MismatchStorageType(MismatchStorageTypeException::deserialize),
    StorageInLock(StorageInLockException::deserialize),

    ComplexOperation(ComplexOperationException::deserialize),
    DuplicateFile(DuplicateFileException::deserialize),
    FileNotFound(FileNotFoundException::deserialize),
    FileInLock(FileInLockException::deserialize),
    UploadChunkIncomplete(UploadChunkIncompleteException::deserialize),

    IllegalSuffix(IllegalSuffixException::deserialize),
    InvalidFilename(InvalidFilenameException::deserialize),
    NameTooLong(NameTooLongException::deserialize),
    ReadOnlyStorage(ReadOnlyStorageException::deserialize),
    SpaceNotEnough(SpaceNotEnoughException::deserialize),
    FlowNotEnough(FlowNotEnoughException::deserialize),
    FileTooLarge(FileTooLargeException::deserialize),
    ;

    public interface CustomExceptions {
        Exceptions identifier();
        void serialize(final MessagePacker packer) throws IOException;
    }

    public interface Deserialize<T extends RuntimeException> {
        T deserialize(final MessageUnpacker unpacker) throws IOException;
    }

    private final Deserialize<RuntimeException> deserialize;

    Exceptions(final Deserialize<RuntimeException> deserialize) {
        this.deserialize = deserialize;
    }

    public Deserialize<RuntimeException> getDeserialize() {
        return this.deserialize;
    }
}
