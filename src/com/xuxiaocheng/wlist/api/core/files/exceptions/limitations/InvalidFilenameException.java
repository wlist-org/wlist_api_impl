package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Throw if the filename contains the invalid character.
 */
public class InvalidFilenameException extends RuntimeException implements Exceptions.CustomExceptions {
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

    @Override
    public Exceptions identifier() {
        return Exceptions.InvalidFilename;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        packer.packString(this.name);
        if (this.optionalCharacter == null) packer.packBoolean(false);
        else packer.packBoolean(true).packInt(this.optionalCharacter);
    }

    public static InvalidFilenameException deserialize(final MessageUnpacker unpacker) throws IOException {
        final String name = unpacker.unpackString();
        final Character optionalCharacter = unpacker.unpackBoolean() ? (char) unpacker.unpackInt() : null;
        return new InvalidFilenameException(name, optionalCharacter);
    }
}
