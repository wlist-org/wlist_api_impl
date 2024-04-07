package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

/**
 * Thrown if the name of storage is conflict.
 */
public class DuplicateStorageException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1211419147906466881L;

    /**
     * Internal constructor.
     */
    private DuplicateStorageException() {
        super("Duplicate storage");
    }
}
