package com.xuxiaocheng.wlist.api.web.exceptions;

import java.io.Serial;

/**
 * Thrown when match the server frequency control.
 * @since 1.1.0
 */
public class MatchFrequencyControlException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5856164291135643527L;

    /**
     * Internal constructor.
     */
    private MatchFrequencyControlException() {
        super();
    }
}
