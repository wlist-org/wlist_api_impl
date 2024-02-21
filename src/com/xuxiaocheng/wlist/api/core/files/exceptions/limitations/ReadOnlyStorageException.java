package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Thrown when creating/uploading/modifying in a read-only storage.
 */
public class ReadOnlyStorageException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5809318309571708372L;

    /**
     * the id of the backend storage.
     */
    protected final long storage;

    /**
     * Internal constructor.
     * @param storage the id of the backend storage.
     */
    private ReadOnlyStorageException(final long storage) {
        super("Readonly storage: " + storage);
        this.storage = storage;
    }

    /**
     * Get the id of the backend storage.
     * @return the id of the backend storage.
     */
    public long getStorage() {
        return this.storage;
    }
}
