package com.xuxiaocheng.wlist.api.impl.data;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

public record InternalUploadInformationCallback(String optionalSha256, byte[] optionalExtra) implements Serializable, Recyclable {
    public static void serialize(final InternalUploadInformationCallback self, final MessagePacker packer) throws IOException {
        if (self.optionalSha256 == null) packer.packBoolean(false);
        else packer.packBoolean(true).packString(self.optionalSha256);
        if (self.optionalExtra == null) packer.packBoolean(false);
        else packer.packBoolean(true).packBinaryHeader(self.optionalExtra.length).addPayload(self.optionalExtra);
    }

    public static InternalUploadInformationCallback deserialize(final MessageUnpacker unpacker) throws IOException {
        final String sha256 = unpacker.unpackBoolean() ? unpacker.unpackString() : null;
        final byte[] extra = unpacker.unpackBoolean() ? unpacker.readPayload(unpacker.unpackBinaryHeader()) : null;
        return new InternalUploadInformationCallback(sha256, extra);
    }
}
