package com.xuxiaocheng.wlist.api.common.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown when match the server frequency control.
 * @since 1.1.0
 */
public class MatchFrequencyControlException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = -5856164291135643527L;

    /**
     * Internal constructor.
     */
    private MatchFrequencyControlException() {
        super("Match frequency control");
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.MatchFrequencyControl;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static MatchFrequencyControlException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new MatchFrequencyControlException();
    }
}
