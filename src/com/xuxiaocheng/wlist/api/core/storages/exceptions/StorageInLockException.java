package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

/**
 * Thrown if the storage is locked.
 */
public class StorageInLockException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4737443365855387892L;

    /**
     * Internal constructor.
     * @param message error message.
     */
    public StorageInLockException(final String message) {
        super(message);
    }
}
