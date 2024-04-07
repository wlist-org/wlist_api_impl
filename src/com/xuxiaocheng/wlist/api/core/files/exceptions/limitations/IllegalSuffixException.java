package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Thrown when a suffix is not allowed for the backend storage.
 * Or the suffix does not match when renamed in some storage.
 */
public class IllegalSuffixException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5220280065799661837L;

    /**
     * The suffix that is not allowed. (Without the dot.)
     */
    protected final String suffix;

    /**
     * Internal constructor.
     * @param suffix the suffix that is not allowed.
     */
    private IllegalSuffixException(final String suffix) {
        super("Illegal suffix: " + suffix);
        this.suffix = suffix;
    }

    /**
     * Get the suffix that is not allowed. (Without the dot.)
     * @return the suffix that is not allowed.
     */
    public String getSuffix() {
        return this.suffix;
    }
}
