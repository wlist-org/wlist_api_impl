package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Throw if the filename contains the invalid code point.
 */
public class InvalidFilenameException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5053028543902405054L;

    /**
     * The name of the backend storage.
     */
    protected final String storage;

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
     * @param storage the name of the backend storage.
     * @param name the name which contains the invalid code point.
     * @param optionalCodePoint the optional invalid code point.
     */
    public InvalidFilenameException(final String storage, final String name, final Integer optionalCodePoint) {
        super(name + " (storage: " + storage + ", codePoint: " + optionalCodePoint + ")");
        this.storage = storage;
        this.name = name;
        this.optionalCodePoint = optionalCodePoint;
    }

    /**
     * Get the name of the backend storage.
     * @return the name of the backend storage.
     */
    public String getStorage() {
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
