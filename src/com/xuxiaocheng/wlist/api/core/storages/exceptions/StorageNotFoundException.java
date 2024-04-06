package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown if the storage not found.
 */
public class StorageNotFoundException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 6368257552600219142L;

    /**
     * Internal constructor.
     */
    private StorageNotFoundException() {
        super();
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.StorageNotFound;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static StorageNotFoundException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new StorageNotFoundException();
    }
}
