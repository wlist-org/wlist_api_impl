package com.xuxiaocheng.wlist.api.common.exceptions;

import java.io.Serial;

/**
 * Wrong userid or password.
 */
public class PasswordNotMatchedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5685188115242899871L;

    /**
     * Internal constructor.
     */
    private PasswordNotMatchedException() {
        super("Password is not matched");
    }
}
