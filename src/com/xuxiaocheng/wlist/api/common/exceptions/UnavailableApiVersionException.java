package com.xuxiaocheng.wlist.api.common.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown if this version of {@code wlist api} is unavailable.
 * This means the server is no longer supported this version of software/app.
 * @see com.xuxiaocheng.wlist.api.common.Marks
 */
public class UnavailableApiVersionException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = -7060493823953922701L;

    /**
     * Internal constructor.
     */
    public UnavailableApiVersionException() {
        super();
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.UnavailableApiVersion;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static UnavailableApiVersionException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new UnavailableApiVersionException();
    }
}
