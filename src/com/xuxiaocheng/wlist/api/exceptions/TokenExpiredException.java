package com.xuxiaocheng.wlist.api.exceptions;

import java.io.Serial;

/**
 * Expired token or invalid token, etc.
 */
public class TokenExpiredException extends Exception {
    @Serial
    private static final long serialVersionUID = -2137962029079296659L;

    /**
     * Internal constructor.
     */
    public TokenExpiredException() {
        super();
    }
}
