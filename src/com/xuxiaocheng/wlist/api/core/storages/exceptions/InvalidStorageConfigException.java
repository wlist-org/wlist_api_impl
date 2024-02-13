package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

/**
 * Thrown when the storage config is invalid.
 */
public class InvalidStorageConfigException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -320110429948047601L;

    /**
     * Internal constructor.
     * @param field the field that is invalid.
     */
    public InvalidStorageConfigException(final String field) {
        super(field);
    }

    /**
     * Get the field.
     * @return the field name.
     */
    public String getField() {
        return this.getMessage();
    }
}
