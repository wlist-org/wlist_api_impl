package com.xuxiaocheng.wlist.api.common.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Expired token or invalid token, etc.
 * Thrown for core token, refresh token, download token, etc.
 */
public class TokenExpiredException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = -2137962029079296659L;

    /**
     * Internal constructor.
     */
    private TokenExpiredException() {
        super("Token is expired");
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.TokenExpired;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static TokenExpiredException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new TokenExpiredException();
    }
}
