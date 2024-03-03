package com.xuxiaocheng.wlist.api.common.exceptions;

import java.io.Serial;

/**
 * Expired token or invalid token, etc.
 * Thrown for core token, refresh token, download token, etc.
 */
public class TokenExpiredException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2137962029079296659L;

    /**
     * Internal constructor.
     */
    private TokenExpiredException() {
        super();
    }
}
