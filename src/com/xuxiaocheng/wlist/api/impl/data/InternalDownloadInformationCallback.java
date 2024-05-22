package com.xuxiaocheng.wlist.api.impl.data;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

public record InternalDownloadInformationCallback(byte[] optionalExtra) implements Serializable, Recyclable {
    public static void serialize(final InternalDownloadInformationCallback self, final MessagePacker packer) throws IOException {
        if (self.optionalExtra == null) packer.packBoolean(false);
        else packer.packBoolean(true).packBinaryHeader(self.optionalExtra.length).addPayload(self.optionalExtra);
    }

    public static InternalDownloadInformationCallback deserialize(final MessageUnpacker unpacker) throws IOException {
        final byte[] extra = unpacker.unpackBoolean() ? unpacker.readPayload(unpacker.unpackBinaryHeader()) : null;
        return new InternalDownloadInformationCallback(extra);
    }
}
