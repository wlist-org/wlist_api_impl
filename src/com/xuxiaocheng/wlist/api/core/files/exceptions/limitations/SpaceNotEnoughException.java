package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Thrown when there is not enough space on the storage.
 */
public class SpaceNotEnoughException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1307043806506563612L;

    /**
     * the id of the backend storage.
     */
    protected final long storage;

    /**
     * The required space.
     */
    protected final long require;

    /**
     * The remaining space. (-1 means unknown.)
     */
    protected final long remaining;

    /**
     * Internal constructor.
     * @param storage the id of the backend storage.
     * @param require the required space.
     * @param remaining the remaining space.
     */
    private SpaceNotEnoughException(final long storage, final long require, final long remaining) {
        super(storage + ": space " + require + (remaining == -1 ? " not enough" : " > " + remaining));
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
     * Get the required space.
     * @return the required space.
     */
    public long getRequire() {
        return this.require;
    }

    /**
     * Get the remaining space. (-1 means unknown)
     * @return the remaining space.
     */
    public long getRemaining() {
        return this.remaining;
    }
}
