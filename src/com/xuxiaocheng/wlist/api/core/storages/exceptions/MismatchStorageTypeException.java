package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

/**
 * Thrown when calling mismatched storage methods.
 */
public class MismatchStorageTypeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6605520003534618096L;

    /**
     * Internal constructor.
     */
    public MismatchStorageTypeException() {
        super();
    }
}
