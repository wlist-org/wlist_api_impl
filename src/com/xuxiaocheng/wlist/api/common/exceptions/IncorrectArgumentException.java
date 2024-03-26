package com.xuxiaocheng.wlist.api.common.exceptions;

import java.io.Serial;

/**
 * Thrown if you pass an incorrect argument to a method.
 * Please check the javadoc to ensure the argument you've passed is correct.
 */
public class IncorrectArgumentException extends Exception {
    @Serial
    private static final long serialVersionUID = 2376130325270336896L;

    /**
     * Internal constructor.
     * @param message error message.
     */
    protected IncorrectArgumentException(final String message) {
        super(message);
    }
}
