package com.xuxiaocheng.wlist.api.exceptions;

import java.io.Serial;

/**
 * A string or other type data to store exceeds the maximum size allowed.
 */
public class TooLargeDataException extends Exception {
    @Serial
    private static final long serialVersionUID = 4670404482830092591L;

    /**
     * Internal constructor.
     */
    public TooLargeDataException() {
        super();
    }
}
