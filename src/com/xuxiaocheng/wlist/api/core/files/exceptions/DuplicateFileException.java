package com.xuxiaocheng.wlist.api.core.files.exceptions;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;

import java.io.Serial;

/**
 * Thrown if the duplication policy is {@link com.xuxiaocheng.wlist.api.core.files.options.Duplicate#Error}.
 */
public class DuplicateFileException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2179754168282049943L;

    /**
     * The parent directory.
     */
    protected final FileLocation parent;

    /**
     * The duplicate filename.
     */
    protected final String name;

    /**
     * Internal constructor.
     * @param parent the parent directory.
     * @param name the duplicate filename.
     */
    private DuplicateFileException(final FileLocation parent, final String name) {
        super("Duplicate file: " + name + " in " + parent);
        this.parent = parent;
        this.name = name;
    }

    /**
     * Get the parent directory.
     * @return the parent directory.
     */
    public FileLocation getParent() {
        return this.parent;
    }

    /**
     * Get the duplicate filename.
     * @return the duplicate filename.
     */
    public String getName() {
        return this.name;
    }
}
