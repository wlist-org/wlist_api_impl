package com.xuxiaocheng.wlist.api.exceptions;

import java.io.Serial;

/**
 * Thrown to indicate that a method has been passed an illegal or inappropriate argument.
 */
public class InvalidArgumentException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = -4058518621790332530L;

    /**
     * Internal constructor.
     * @param message error message
     */
    public InvalidArgumentException(final String message) {
        super(message);
    }
}
