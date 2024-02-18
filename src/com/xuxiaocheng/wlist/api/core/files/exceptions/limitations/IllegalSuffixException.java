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
     * The name of the backend storage.
     */
    protected final String storage;

    /**
     * The suffix that is not allowed. (Without the dot.)
     */
    protected final String suffix;

    /**
     * Internal constructor.
     * @param storage the name of the backend storage.
     * @param suffix the suffix that is not allowed.
     */
    public IllegalSuffixException(final String storage, final String suffix) {
        super(storage + ": Illegal suffix: " + suffix);
        this.storage = storage;
        this.suffix = suffix;
    }

    /**
     * Get the name of the backend storage.
     * @return the name of the backend storage.
     */
    public String getStorage() {
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
