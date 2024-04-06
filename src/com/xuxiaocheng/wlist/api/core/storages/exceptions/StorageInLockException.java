package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown if the storage is locked.
 */
public class StorageInLockException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = -4737443365855387892L;

    /**
     * The locked type.
     */
    protected final String type;

    /**
     * Internal constructor.
     * @param type the locked type.
     */
    private StorageInLockException(final String type) {
        super(type);
        this.type = type;
    }

    /**
     * Get the locked type.
     * @return the locked type.
     */
    public String getType() {
        return this.type;
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.StorageInLock;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        packer.packString(this.type);
    }

    public static StorageInLockException deserialize(final MessageUnpacker unpacker) throws IOException {
        return new StorageInLockException(unpacker.unpackString());
    }
}
