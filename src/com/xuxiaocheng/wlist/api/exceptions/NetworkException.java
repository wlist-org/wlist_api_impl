package com.xuxiaocheng.wlist.api.exceptions;

import java.io.Serial;

/**
 * A special exception from internal exception for network error.
 */
public class NetworkException extends InternalException {
    @Serial
    private static final long serialVersionUID = 8568583877107316060L;

    /**
     * Internal constructor.
     * @param message error message.
     */
    private NetworkException(final String message) {
        super(message);
    }
}
