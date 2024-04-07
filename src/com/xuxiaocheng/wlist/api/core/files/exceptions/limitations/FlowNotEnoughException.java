package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown when the transmission flow of the storage is not enough.
 */
public class FlowNotEnoughException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = -6252572995937609898L;

    /**
     * True means upload flow, false means download flow.
     */
    protected final boolean isUpload;

    /**
     * The required flow.
     */
    protected final long require;

    /**
     * The remaining flow. (-1 means unknown.)
     */
    protected final long remaining;

    /**
     * Internal constructor.
     * @param isUpload whether upload flow or download flow.
     * @param require the required flow.
     * @param remaining the remaining flow.
     */
    private FlowNotEnoughException(final boolean isUpload, final long require, final long remaining) {
        super((isUpload ? "upload" : "download") + " flow " + require + (remaining == -1 ? " not enough" : " > " + remaining));
        assert remaining == -1 || require > remaining;
        this.isUpload = isUpload;
        this.require = require;
        this.remaining = remaining;
    }

    /**
     * Whether upload flow or download flow.
     * @return true means upload flow, false means download flow.
     */
    public boolean isUpload() {
        return this.isUpload;
    }

    /**
     * Get the required flow.
     * @return the required flow.
     */
    public long getRequire() {
        return this.require;
    }

    /**
     * Get the remaining flow. (-1 means unknown)
     * @return the remaining flow.
     */
    public long getRemaining() {
        return this.remaining;
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.FlowNotEnough;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        packer.packBoolean(this.isUpload);
        packer.packLong(this.require);
        packer.packLong(this.remaining);
    }

    public static FlowNotEnoughException deserialize(final MessageUnpacker unpacker) throws IOException {
        final boolean isUpload = unpacker.unpackBoolean();
        final long require = unpacker.unpackLong();
        final long remaining = unpacker.unpackLong();
        return new FlowNotEnoughException(isUpload, require, remaining);
    }
}
