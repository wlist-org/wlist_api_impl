package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Thrown when creating/uploading/modifying in a read-only storage.
 */
public class ReadOnlyStorageException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5809318309571708372L;

    /**
     * Internal constructor.
     */
    private ReadOnlyStorageException() {
        super("Readonly storage");
    }
}
