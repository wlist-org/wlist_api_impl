package com.xuxiaocheng.wlist.api.common.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Wrong userid or password.
 */
public class PasswordMismatchedException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 5685188115242899871L;

    /**
     * Internal constructor.
     */
    private PasswordMismatchedException() {
        super("Password is mismatched");
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.PasswordMismatched;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static PasswordMismatchedException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new PasswordMismatchedException();
    }
}
