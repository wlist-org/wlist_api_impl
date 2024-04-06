package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

/**
 * Thrown if the storage is locked.
 */
public class StorageInLockException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4737443365855387892L;

    /**
     * The locked type.
     */
    protected final String type;

    /**
     * Internal constructor.
     * @param type the locked type.
     */
    private StorageInLockException(final String type) {
        super(type);
        this.type = type;
    }

    /**
     * Get the locked type.
     * @return the locked type.
     */
    public String getType() {
        return this.type;
    }
}
