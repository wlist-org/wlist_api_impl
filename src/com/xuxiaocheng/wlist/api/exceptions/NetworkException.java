package com.xuxiaocheng.wlist.api.exceptions;

import java.io.IOException;
import java.io.Serial;

/**
 * A special exception from internal exception for network error.
 */
public class NetworkException extends InternalException {
    @Serial
    private static final long serialVersionUID = 8568583877107316060L;

    /**
     * Internal constructor.
     * @param message error message
     */
    public NetworkException(final String message) {
        super(message);
    }
}
