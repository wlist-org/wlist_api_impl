package com.xuxiaocheng.wlist.api.exceptions;

import java.io.Serial;

public class InvalidArgumentException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = -4058518621790332530L;

    public InvalidArgumentException(final String message) {
        super(message);
    }
}
