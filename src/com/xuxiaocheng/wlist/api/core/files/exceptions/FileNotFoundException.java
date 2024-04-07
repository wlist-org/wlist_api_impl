package com.xuxiaocheng.wlist.api.core.files.exceptions;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown if the file/directory does not exist.
 */
public class FileNotFoundException extends RuntimeException implements Exceptions.CustomExceptions {
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
    private FileNotFoundException(final FileLocation location) {
        super("File " + location + " not found.");
        this.location = location;
    }

    /**
     * Get the location not found.
     * @return the location which not found.
     */
    public FileLocation getLocation() {
        return this.location;
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.FileNotFound;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        FileLocation.serialize(this.location, packer);
    }

    public static FileNotFoundException deserialize(final MessageUnpacker unpacker) throws IOException {
        final FileLocation parent = FileLocation.deserialize(unpacker);
        return new FileNotFoundException(parent);
    }
}
