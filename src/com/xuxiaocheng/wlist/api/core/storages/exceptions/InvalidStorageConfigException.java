package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

/**
 * Thrown when the storage config is invalid.
 */
public class InvalidStorageConfigException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -320110429948047601L;

    /**
     * The field that is invalid.
     */
    protected final String field;

    /**
     * Internal constructor.
     * @param field the field that is invalid.
     */
    protected InvalidStorageConfigException(final String field) {
        super("Invalid: " + field);
        this.field = field;
    }

    /**
     * Get the field name that is invalid.
     * @return the field name.
     */
    public String getField() {
        return this.field;
    }
}
