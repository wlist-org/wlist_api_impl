package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Throw if the filename contains the invalid code point.
 */
public class InvalidFilenameException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5053028543902405054L;

    /**
     * the id of the backend storage.
     */
    protected final long storage;

    /**
     * The name which contains the invalid code point.
     */
    protected final String name;

    /**
     * The optional(nullable) invalid code point.
     */
    protected final Integer optionalCodePoint;

    /**
     * Internal constructor.
     * @param storage the id of the backend storage.
     * @param name the name which contains the invalid code point.
     * @param optionalCodePoint the optional invalid code point.
     */
    public InvalidFilenameException(final long storage, final String name, final Integer optionalCodePoint) {
        super(name + " (storage: " + storage + ", codePoint: " + optionalCodePoint + ")");
        this.storage = storage;
        this.name = name;
        this.optionalCodePoint = optionalCodePoint;
    }

    /**
     * Get the id of the backend storage.
     * @return the id of the backend storage.
     */
    public long getStorage() {
        return this.storage;
    }

    /**
     * Get the name which contains the invalid code point.
     * @return the name which contains the invalid code point.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the optional(nullable) invalid code point.
     * @return the optional(nullable) invalid code point.
     */
    public Integer getOptionalCodePoint() {
        return this.optionalCodePoint;
    }
}
