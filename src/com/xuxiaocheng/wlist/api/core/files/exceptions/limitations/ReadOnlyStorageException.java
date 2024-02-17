package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Thrown when creating/uploading/modifying in a read-only storage.
 */
public class ReadOnlyStorageException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5809318309571708372L;

    /**
     * The name of the backend storage.
     */
    protected final String storage;

    /**
     * Internal constructor.
     * @param storage the name of the backend storage.
     */
    public ReadOnlyStorageException(final String storage) {
        super(storage);
        this.storage = storage;
    }

    /**
     * Get the name of the backend storage.
     * @return the name of the backend storage.
     */
    public String getStorage() {
        return this.storage;
    }
}
