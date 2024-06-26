package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown if the name of storage is conflict.
 */
public class DuplicateStorageException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 1211419147906466881L;

    /**
     * Internal constructor.
     */
    private DuplicateStorageException() {
        super("Duplicate storage");
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.DuplicateStorage;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static DuplicateStorageException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new DuplicateStorageException();
    }
}
