package com.xuxiaocheng.wlist.api.common.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * A string or other type data to store exceeds the maximum size allowed.
 */
public class TooLargeDataException extends IncorrectArgumentException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 4670404482830092591L;

    /**
     * Internal constructor.
     */
    private TooLargeDataException() {
        super("too large data");
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.TooLargeData;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static TooLargeDataException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new TooLargeDataException();
    }
}
