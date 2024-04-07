package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown when creating/uploading/modifying in a read-only storage.
 */
public class ReadOnlyStorageException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = -5809318309571708372L;

    /**
     * Internal constructor.
     */
    private ReadOnlyStorageException() {
        super("Readonly storage");
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.ReadOnlyStorage;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static ReadOnlyStorageException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new ReadOnlyStorageException();
    }
}
