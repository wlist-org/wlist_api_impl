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
     * The locked type.
     */
    protected final String type;

    /**
     * Internal constructor.
     * @param location the location of the locked file/directory.
     * @param type the locked type.
     */
    private FileInLockException(final FileLocation location, final String type) {
        super(location.toString() + ": " + type);
        this.location = location;
        this.type = type;
    }

    /**
     * Get the location of the locked file/directory.
     * @return the location of the locked file/directory.
     */
    public FileLocation getLocation() {
        return this.location;
    }

    /**
     * Get the locked type.
     * @return the locked type.
     */
    public String getType() {
        return this.type;
    }
}
