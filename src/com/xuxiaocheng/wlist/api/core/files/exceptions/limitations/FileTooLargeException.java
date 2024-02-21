package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Thrown when the maximum single file size limit for the storage is exceeded.
 */
public class FileTooLargeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8726792675627293257L;

    /**
     * The id of the backend storage.
     */
    protected final long storage;

    /**
     * The required filesize.
     */
    protected final long require;

    /**
     * The limitation. (-1 means unknown.)
     */
    protected final long limitation;

    /**
     * Internal constructor.
     * @param storage the id of the backend storage.
     * @param require the required filesize.
     * @param limitation the limitation.
     */
    private FileTooLargeException(final long storage, final long require, final long limitation) {
        super(storage + ": file " + require + (limitation == -1 ? " too large" : " > " + limitation));
        assert limitation == -1 || require > limitation;
        this.storage = storage;
        this.require = require;
        this.limitation = limitation;
    }

    /**
     * Get the id of the backend storage.
     * @return the id of the backend storage.
     */
    public long getStorage() {
        return this.storage;
    }

    /**
     * Get the required filesize.
     * @return the required filesize.
     */
    public long getRequire() {
        return this.require;
    }

    /**
     * Get the limitation. (-1 means unknown)
     * @return the limitation.
     */
    public long getLimitation() {
        return this.limitation;
    }
}
