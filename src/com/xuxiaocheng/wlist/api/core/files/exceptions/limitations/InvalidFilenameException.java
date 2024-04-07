package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Throw if the filename contains the invalid code point.
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
    protected final Integer optionalCodePoint;

    /**
     * Internal constructor.
     * @param name the name which contains the invalid code point.
     * @param optionalCodePoint the optional invalid code point.
     */
    private InvalidFilenameException(final String name, final Integer optionalCodePoint) {
        super("Invalid filename: " + name + " (codePoint: " + optionalCodePoint + ")");
        this.name = name;
        this.optionalCodePoint = optionalCodePoint;
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

    @Override
    public Exceptions identifier() {
        return Exceptions.InvalidFilename;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        packer.packString(this.name);
        if (this.optionalCodePoint == null) {
            packer.packBoolean(false);
        } else {
            packer.packBoolean(true);
            packer.packInt(this.optionalCodePoint);
        }
    }

    public static InvalidFilenameException deserialize(final MessageUnpacker unpacker) throws IOException {
        final String name = unpacker.unpackString();
        final Integer optionalCodePoint = unpacker.unpackBoolean() ? unpacker.unpackInt() : null;
        return new InvalidFilenameException(name, optionalCodePoint);
    }
}
