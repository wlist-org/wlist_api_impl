package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

/**
 * Thrown when the storage config is invalid.
 */
public class InvalidStorageConfigException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -320110429948047601L;

    public InvalidStorageConfigException(final String field) {
        super(field);
    }
}
