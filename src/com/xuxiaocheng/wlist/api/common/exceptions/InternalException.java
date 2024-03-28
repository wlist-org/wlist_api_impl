package com.xuxiaocheng.wlist.api.common.exceptions;

import java.io.Serial;

/**
 * A general exception for internal errors.
 */
public class InternalException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5236605863550037592L;

    /**
     * Internal constructor.
     * @param message error message.
     */
    public InternalException(final String message) {
        super(message);
    }

    /**
     * Internal constructor.
     * @param message error message.
     * @param cause error cause.
     */
    public InternalException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
