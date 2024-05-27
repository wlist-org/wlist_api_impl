package com.xuxiaocheng.wlist.api.common.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * A special exception from internal exception for network error.
 */
public class NetworkException extends InternalException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 8568583877107316060L;

    /**
     * Internal constructor.
     * @param message error message.
     */
    public NetworkException(final String message) {
        super(message);
    }

    /**
     * Internal constructor.
     * @param message error message.
     * @param cause error cause.
     */
    public NetworkException(final String message, final Throwable cause) {
        super(message, cause);
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.Network;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        packer.packString(this.getMessage());
    }

    public static NetworkException deserialize(final MessageUnpacker unpacker) throws IOException {
        final String message = unpacker.unpackString();
        return new NetworkException(message);
    }
}
