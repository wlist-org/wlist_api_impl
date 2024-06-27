package com.xuxiaocheng.wlist.api.impl.data;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.information.UploadChunkInformation;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

public record InternalUploadInformation(
        long start,
        long len,
        UploadChunkInformation chunk,
        String optionalUrl,
        String optionalToken,
        byte[] optionalExtra
) implements Serializable, Recyclable {
    public static void serialize(final InternalUploadInformation self, final MessagePacker packer) throws IOException {
        packer.packLong(self.start);
        packer.packLong(self.len);
        UploadChunkInformation.serialize(self.chunk, packer);
        if (self.optionalUrl == null) packer.packBoolean(false);
        else packer.packBoolean(true).packString(self.optionalUrl);
        if (self.optionalToken == null) packer.packBoolean(false);
        else packer.packBoolean(true).packString(self.optionalToken);
        if (self.optionalExtra == null) packer.packBoolean(false);
        else packer.packBoolean(true).packBinaryHeader(self.optionalExtra.length).addPayload(self.optionalExtra);
    }

    public static InternalUploadInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final long start = unpacker.unpackLong();
        final long len = unpacker.unpackLong();
        final UploadChunkInformation chunk = UploadChunkInformation.deserialize(unpacker);
        final String url = unpacker.unpackBoolean() ? unpacker.unpackString() : null;
        final String token = unpacker.unpackBoolean() ? unpacker.unpackString() : null;
        final byte[] extra = unpacker.unpackBoolean() ? unpacker.readPayload(unpacker.unpackBinaryHeader()) : null;
        return new InternalUploadInformation(start, len, chunk, url, token, extra);
    }
}
