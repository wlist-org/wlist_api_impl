package com.xuxiaocheng.wlist.api.common.exceptions;

import java.io.Serial;

/**
 * Wrong userid or password.
 */
public class PasswordMismatchedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5685188115242899871L;

    /**
     * Internal constructor.
     */
    private PasswordMismatchedException() {
        super("Password is mismatched");
    }
}
