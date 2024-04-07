package com.xuxiaocheng.wlist.api.core.files.exceptions.limitations;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown when a file/directory name is too long.
 * This is caused by the backend storage.
 * <p>Unlike {@link com.xuxiaocheng.wlist.api.common.exceptions.TooLargeDataException}, that is an exception thrown by the core server.</p>
 */
public class NameTooLongException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 6260690462739069062L;

    /**
     * The name which is too long. (Added the indexed suffix.)
     */
    protected final String name;

    /**
     * The limitation. (-1 means unknown.)
     */
    protected final long limitation;

    /**
     * Internal constructor.
     * @param name the too long name.
     */
    private NameTooLongException(final String name, final long limitation) {
        super("name length " + name.length() + (limitation == -1 ? " too long" : " > " + limitation));
        assert limitation == -1 || name.length() > limitation;
        this.name = name;
        this.limitation = limitation;
    }

    /**
     * Get the too long name.
     * @return the too long name.
     */
    public String getName() {
        return this.name;
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
        return Exceptions.NameTooLong;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        packer.packString(this.name);
        packer.packLong(this.limitation);
    }

    public static NameTooLongException deserialize(final MessageUnpacker unpacker) throws IOException {
        final String name = unpacker.unpackString();
        final long limitation = unpacker.unpackLong();
        return new NameTooLongException(name, limitation);
    }
}
