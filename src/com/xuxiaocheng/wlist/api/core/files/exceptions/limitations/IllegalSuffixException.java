package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Thrown when a suffix is not allowed for the backend storage.
 * Or the suffix does not match when renamed.
 */
public class IllegalSuffixException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5220280065799661837L;

    /**
     * The id of the backend storage.
     */
    protected final long storage;

    /**
     * The suffix that is not allowed. (Without the dot.)
     */
    protected final String suffix;

    /**
     * Internal constructor.
     * @param storage the id of the backend storage.
     * @param suffix the suffix that is not allowed.
     */
    private IllegalSuffixException(final long storage, final String suffix) {
        super(storage + ": Illegal suffix: " + suffix);
        this.storage = storage;
        this.suffix = suffix;
    }

    /**
     * Get the id of the backend storage.
     * @return the id of the backend storage.
     */
    public long getStorage() {
        return this.storage;
    }

    /**
     * Get the suffix that is not allowed. (Without the dot.)
     * @return the suffix that is not allowed.
     */
    public String getSuffix() {
        return this.suffix;
    }
}
