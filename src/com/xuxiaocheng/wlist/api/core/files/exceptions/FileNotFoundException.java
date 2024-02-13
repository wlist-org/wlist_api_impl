package com.xuxiaocheng.wlist.api.core.files.exceptions;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;

import java.io.Serial;

/**
 * Thrown if the file/directory does not exist.
 */
public class FileNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2735411178138322925L;

    /**
     * The location which not found.
     */
    protected final FileLocation location;

    /**
     * Internal constructor.
     * @param location the location which not found.
     */
    public FileNotFoundException(final FileLocation location) {
        super(location.toString()+ " not found.");
        this.location = location;
    }

    /**
     * Get the location not found.
     * @return the location which not found.
     */
    public FileLocation getLocation() {
        return this.location;
    }
}
