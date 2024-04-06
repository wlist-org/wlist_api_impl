package com.xuxiaocheng.wlist.api.core.files.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown when the operation is complex. (Complex means the operation cannot be done in one step.)
 * This means you need to break down this operation into small pieces.
 * Although this exception is not checked, you should handle it as checked.
 */
public class ComplexOperationException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = -8826485751328024761L;

    /**
     * Internal constructor.
     */
    private ComplexOperationException() {
        super();
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.ComplexOperation;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static ComplexOperationException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new ComplexOperationException();
    }
}
