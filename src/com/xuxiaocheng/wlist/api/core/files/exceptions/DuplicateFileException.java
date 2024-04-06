package com.xuxiaocheng.wlist.api.core.files.exceptions;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown if the duplication policy is {@link com.xuxiaocheng.wlist.api.core.files.options.Duplicate#Error}.
 */
public class DuplicateFileException extends RuntimeException implements Exceptions.CustomExceptions {
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
        super(parent + "/" + name);
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

    @Override
    public Exceptions identifier() {
        return Exceptions.DuplicateFile;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        FileLocation.serialize(this.parent, packer);
        packer.packString(this.name);
    }

    public static DuplicateFileException deserialize(final MessageUnpacker unpacker) throws IOException {
        final FileLocation parent = FileLocation.deserialize(unpacker);
        final String name = unpacker.unpackString();
        return new DuplicateFileException(parent, name);
    }
}
