package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown when calling mismatched storage methods.
 */
public class StorageTypeMismatchedException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 6605520003534618096L;

    /**
     * Internal constructor.
     */
    private StorageTypeMismatchedException() {
        super("Storage type is mismatched");
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.StorageTypeMismatched;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static StorageTypeMismatchedException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new StorageTypeMismatchedException();
    }
}
