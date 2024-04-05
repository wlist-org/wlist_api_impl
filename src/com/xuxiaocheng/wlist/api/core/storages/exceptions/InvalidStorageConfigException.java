package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;
import java.util.Collections;
import java.util.Map;

/**
 * Thrown when the storage config is invalid.
 */
public class InvalidStorageConfigException extends RuntimeException {
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
        super("Invalid: " + messages);
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
}
