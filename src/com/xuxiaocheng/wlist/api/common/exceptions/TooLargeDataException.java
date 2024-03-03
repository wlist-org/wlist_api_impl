package com.xuxiaocheng.wlist.api.common.exceptions;

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
    private TooLargeDataException() {
        super();
    }
}
