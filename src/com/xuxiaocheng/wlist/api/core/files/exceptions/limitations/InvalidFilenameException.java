package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import java.io.Serial;

/**
 * Throw if the filename contains the invalid character.
 */
public class InvalidFilenameException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5053028543902405054L;

    /**
     * The name which contains the invalid code point.
     */
    protected final String name;

    /**
     * The optional(nullable) invalid code point.
     */
    protected final Character optionalCharacter;

    /**
     * Internal constructor.
     * @param name the name which contains the invalid character.
     * @param optionalCharacter the optional invalid character.
     */
    private InvalidFilenameException(final String name, final Character optionalCharacter) {
        super("Invalid filename: " + name + " (character: " + optionalCharacter + ")");
        this.name = name;
        this.optionalCharacter = optionalCharacter;
    }

    /**
     * Get the name which contains the invalid code point.
     * @return the name which contains the invalid code point.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the optional(nullable) invalid character.
     * @return the optional(nullable) invalid character.
     */
    public Character getOptionalCharacter() {
        return this.optionalCharacter;
    }
}
