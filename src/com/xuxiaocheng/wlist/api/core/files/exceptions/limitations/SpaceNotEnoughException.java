package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown when there is not enough space on the storage.
 */
public class SpaceNotEnoughException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 1307043806506563612L;

    /**
     * The required space.
     */
    protected final long require;

    /**
     * The remaining space. (-1 means unknown.)
     */
    protected final long remaining;

    /**
     * Internal constructor.
     * @param require the required space.
     * @param remaining the remaining space.
     */
    private SpaceNotEnoughException(final long require, final long remaining) {
        super("space " + require + (remaining == -1 ? " not enough" : " > " + remaining));
        assert remaining == -1 || require > remaining;
        this.require = require;
        this.remaining = remaining;
    }

    /**
     * Get the required space.
     * @return the required space.
     */
    public long getRequire() {
        return this.require;
    }

    /**
     * Get the remaining space. (-1 means unknown)
     * @return the remaining space.
     */
    public long getRemaining() {
        return this.remaining;
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.SpaceNotEnough;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        packer.packLong(this.require);
        packer.packLong(this.remaining);
    }

    public static SpaceNotEnoughException deserialize(final MessageUnpacker unpacker) throws IOException {
        final long require = unpacker.unpackLong();
        final long remaining = unpacker.unpackLong();
        return new SpaceNotEnoughException(require, remaining);
    }
}
