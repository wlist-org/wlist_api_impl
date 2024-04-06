package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown when the maximum single file size limit for the storage is exceeded.
 */
public class FileTooLargeException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = -8726792675627293257L;

    /**
     * The id of the backend storage.
     */
    protected final long storage;

    /**
     * The required filesize.
     */
    protected final long require;

    /**
     * The limitation. (-1 means unknown.)
     */
    protected final long limitation;

    /**
     * Internal constructor.
     * @param storage the id of the backend storage.
     * @param require the required filesize.
     * @param limitation the limitation.
     */
    private FileTooLargeException(final long storage, final long require, final long limitation) {
        super(storage + ": file " + require + (limitation == -1 ? " too large" : " > " + limitation));
        assert limitation == -1 || require > limitation;
        this.storage = storage;
        this.require = require;
        this.limitation = limitation;
    }

    /**
     * Get the id of the backend storage.
     * @return the id of the backend storage.
     */
    public long getStorage() {
        return this.storage;
    }

    /**
     * Get the required filesize.
     * @return the required filesize.
     */
    public long getRequire() {
        return this.require;
    }

    /**
     * Get the limitation. (-1 means unknown)
     * @return the limitation.
     */
    public long getLimitation() {
        return this.limitation;
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.FileTooLarge;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        packer.packLong(this.storage);
        packer.packLong(this.require);
        packer.packLong(this.limitation);
    }

    public static FileTooLargeException deserialize(final MessageUnpacker unpacker) throws IOException {
        final long storage = unpacker.unpackLong();
        final long require = unpacker.unpackLong();
        final long limitation = unpacker.unpackLong();
        return new FileTooLargeException(storage, require, limitation);
    }
}
