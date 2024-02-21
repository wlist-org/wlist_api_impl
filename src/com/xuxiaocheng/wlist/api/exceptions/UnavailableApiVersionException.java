package com.xuxiaocheng.wlist.api.exceptions;

import java.io.Serial;

/**
 * Thrown if this version of {@code wlist api} is unavailable.
 * This means the server is no longer supported this version of software/app.
 * @see com.xuxiaocheng.wlist.api.common.Marks
 */
public class UnavailableApiVersionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7060493823953922701L;

    /**
     * Internal constructor.
     */
    private UnavailableApiVersionException() {
        super();
    }
}
