package com.xuxiaocheng.wlist.api.core.files.exceptions;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;

import java.io.Serial;

/**
 * Thrown if the file/directory is locked.
 */
public class FileInLockException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2802445176796214285L;

    /**
     * The location of the locked file/directory.
     */
    protected final FileLocation location;

    /**
     * Internal constructor.
     * @param location the location of the locked file/directory.
     * @param message error message.
     */
    public FileInLockException(final FileLocation location, final String message) {
        super(location.toString() + ": " + message);
        this.location = location;
    }

    /**
     * Get the location of the locked file/directory.
     * @return the location of the locked file/directory.
     */
    public FileLocation getLocation() {
        return this.location;
    }
}
