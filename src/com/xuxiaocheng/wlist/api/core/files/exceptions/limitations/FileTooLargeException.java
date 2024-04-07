package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Thrown when the maximum single file size limit for the storage is exceeded.
 */
public class FileTooLargeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8726792675627293257L;

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
     * @param require the required filesize.
     * @param limitation the limitation.
     */
    private FileTooLargeException(final long require, final long limitation) {
        super("file size " + require + (limitation == -1 ? " too large" : " > " + limitation));
        assert limitation == -1 || require > limitation;
        this.require = require;
        this.limitation = limitation;
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
