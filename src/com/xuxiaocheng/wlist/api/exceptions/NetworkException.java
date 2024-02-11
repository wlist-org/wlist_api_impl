package com.xuxiaocheng.wlist.api.exceptions;

import java.io.IOException;
import java.io.Serial;

/**
 * A special exception from internal exception for network error.
 */
public class NetworkException extends IOException {
    @Serial
    private static final long serialVersionUID = 8568583877107316060L;

    public NetworkException(final String message) {
        super(message);
    }
}
