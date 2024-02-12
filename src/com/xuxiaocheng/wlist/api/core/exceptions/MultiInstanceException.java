package com.xuxiaocheng.wlist.api.core.exceptions;

import java.io.Serial;

public class MultiInstanceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4075055876580871265L;

    public MultiInstanceException() {
        super();
    }
}
