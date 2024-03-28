package com.xuxiaocheng.wlist.api.common.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown if you pass an incorrect argument to a method.
 * Please check the javadoc to ensure the argument you've passed is correct.
 */
public class IncorrectArgumentException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 2376130325270336896L;

    /**
     * Internal constructor.
     * @param message error message.
     */
    protected IncorrectArgumentException(final String message) {
        super(message);
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.IncorrectArgument;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        packer.packString(this.getMessage());
    }

    public static IncorrectArgumentException deserialize(final MessageUnpacker unpacker) throws IOException {
        return new IncorrectArgumentException(unpacker.unpackString());
    }
}
