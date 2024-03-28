package com.xuxiaocheng.wlist.api.impl.enums;

import com.xuxiaocheng.wlist.api.common.exceptions.IncorrectArgumentException;
import com.xuxiaocheng.wlist.api.common.exceptions.NetworkException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;

public enum Exceptions {
    Internal(unpacker -> new NetworkException("Server error.")),

    IncorrectArgument(IncorrectArgumentException::deserialize),
    // TODO
    ;

    public interface CustomExceptions {
        Exceptions identifier();
        void serialize(final MessagePacker packer) throws IOException;
    }

    public interface Deserialize<T extends RuntimeException> {
        T deserialize(final MessageUnpacker unpacker) throws IOException;
    }

    private final Deserialize<RuntimeException> deserialize;

    Exceptions(final Deserialize<RuntimeException> deserialize) {
        this.deserialize = deserialize;
    }

    public Deserialize<RuntimeException> getDeserialize() {
        return this.deserialize;
    }
}
