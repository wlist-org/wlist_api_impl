package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Thrown when the transmission flow of the storage is not enough.
 */
public class FlowNotEnoughException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6252572995937609898L;

    /**
     * The id of the backend storage.
     */
    protected final long storage;

    /**
     * The required flow.
     */
    protected final long require;

    /**
     * The remaining flow. (-1 means unknown.)
     */
    protected final long remaining;

    /**
     * Internal constructor.
     * @param storage the id of the backend storage.
     * @param require the required flow.
     * @param remaining the remaining flow.
     */
    private FlowNotEnoughException(final long storage, final long require, final long remaining) {
        super(storage + ": flow " + require + (remaining == -1 ? " not enough" : " > " + remaining));
        assert remaining == -1 || require > remaining;
        this.storage = storage;
        this.require = require;
        this.remaining = remaining;
    }

    /**
     * Get the id of the backend storage.
     * @return the id of the backend storage.
     */
    public long getStorage() {
        return this.storage;
    }

    /**
     * Get the required flow.
     * @return the required flow.
     */
    public long getRequire() {
        return this.require;
    }

    /**
     * Get the remaining flow. (-1 means unknown)
     * @return the remaining flow.
     */
    public long getRemaining() {
        return this.remaining;
    }
}
