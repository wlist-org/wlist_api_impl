package com.xuxiaocheng.wlist.api.web.exceptions;

import java.io.Serial;

/**
 * Wrong userid or password.
 */
public class PasswordNotMatchedException extends Exception {
    @Serial
    private static final long serialVersionUID = 5685188115242899871L;

    /**
     * Internal constructor.
     */
    private PasswordNotMatchedException() {
        super();
    }
}
