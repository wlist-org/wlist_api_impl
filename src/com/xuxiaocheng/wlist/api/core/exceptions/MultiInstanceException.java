package com.xuxiaocheng.wlist.api.core.exceptions;

import java.io.Serial;

/**
 * Thrown if called {@link com.xuxiaocheng.wlist.api.core.Server#start(int)} twice.
 */
public class MultiInstanceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4075055876580871265L;

    /**
     * Internal constructor.
     */
    public MultiInstanceException() {
        super();
    }
}
