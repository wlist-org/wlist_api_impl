package com.xuxiaocheng.wlist.api.core.files.exceptions;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;

import java.io.Serial;

/**
 * Thrown if the file/directory not found.
 */
public class FileNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2735411178138322925L;

    protected final FileLocation location;

    /**
     * Internal constructor.
     */
    public FileNotFoundException(final FileLocation location) {
        super(location.toString()+ " not found.");
        this.location = location;
    }

    public FileLocation getLocation() {
        return this.location;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
