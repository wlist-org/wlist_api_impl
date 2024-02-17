package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Thrown when the maximum single file size limit for the storage is exceeded.
 */
public class FileTooLargeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8726792675627293257L;

    /**
     * The name of the backend storage.
     */
    protected final String storage;

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
     * @param storage the name of the backend storage.
     * @param require the required filesize.
     * @param limitation the limitation.
     */
    public FileTooLargeException(final String storage, final long require, final long limitation) {
        super(storage + ": file " + require + (limitation == -1 ? " too large" : " > " + limitation));
        assert limitation == -1 || require > limitation;
        this.storage = storage;
        this.require = require;
        this.limitation = limitation;
    }

    /**
     * Get the name of the backend storage.
     * @return the name of the backend storage.
     */
    public String getStorage() {
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
