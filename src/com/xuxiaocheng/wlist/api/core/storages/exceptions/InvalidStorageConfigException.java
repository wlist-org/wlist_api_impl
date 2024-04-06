package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Thrown when the storage config is invalid.
 */
public class InvalidStorageConfigException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = -320110429948047601L;

    /**
     * The invalid field map.
     * The key is the name of the invalid field.
     * The value is the error message.
     */
    protected final Map<String, String> messages;

    /**
     * The extra field name, the key of a global error message.
     */
    public static final String Extra = "extra";

    /**
     * Internal constructor.
     * @param messages the field that is invalid.
     */
    protected InvalidStorageConfigException(final Map<String, String> messages) {
        super("Invalid config: " + messages);
        this.messages = Collections.unmodifiableMap(messages);
    }

    /**
     * Get the invalid field map.
     * @return the invalid field map.
     * @see #messages
     */
    public Map<String, String> getMessages() {
        return this.messages;
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.InvalidStorageConfig;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
        packer.packMapHeader(this.messages.size());
        for (final Map.Entry<String, String> entry: this.messages.entrySet()) {
            packer.packString(entry.getKey());
            packer.packString(entry.getValue());
        }
    }

    public static InvalidStorageConfigException deserialize(final MessageUnpacker unpacker) throws IOException {
        final int size = unpacker.unpackMapHeader();
        final Map<String, String> map = new HashMap<>(size);
        for (int i = 0; i < size; ++i) {
            final String key = unpacker.unpackString();
            final String value = unpacker.unpackString();
            map.put(key, value);
        }
        return new InvalidStorageConfigException(map);
    }
}
