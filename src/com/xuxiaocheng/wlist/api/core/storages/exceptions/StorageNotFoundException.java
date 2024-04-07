package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

/**
 * Thrown if the storage not found.
 */
public class StorageNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6368257552600219142L;

    /**
     * Internal constructor.
     */
    private StorageNotFoundException() {
        super("Storage not found");
    }
}
