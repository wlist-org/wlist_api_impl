package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown when a suffix is not allowed for the backend storage.
 * Or the suffix does not match when renamed in some storage.
 */
public class IllegalSuffixException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 5220280065799661837L;

    /**
     * The suffix that is not allowed. (Without the dot.)
     */
    protected final String suffix;

    /**
     * Internal constructor.
     * @param suffix the suffix that is not allowed.
     */
    private IllegalSuffixException(final String suffix) {
        super("Illegal suffix: " + suffix);
        this.suffix = suffix;
    }

    /**
     * Get the suffix that is not allowed. (Without the dot.)
     * @return the suffix that is not allowed.
     */
    public String getSuffix() {
        return this.suffix;
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.IllegalSuffix;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        packer.packString(this.suffix);
    }

    public static IllegalSuffixException deserialize(final MessageUnpacker unpacker) throws IOException {
        final String suffix = unpacker.unpackString();
        return new IllegalSuffixException(suffix);
    }
}
