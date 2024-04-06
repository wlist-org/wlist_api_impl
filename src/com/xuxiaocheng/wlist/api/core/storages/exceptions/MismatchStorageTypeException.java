package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown when calling mismatched storage methods.
 */
public class MismatchStorageTypeException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 6605520003534618096L;

    /**
     * Internal constructor.
     */
    private MismatchStorageTypeException() {
        super();
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.MismatchStorageType;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static MismatchStorageTypeException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new MismatchStorageTypeException();
    }
}
