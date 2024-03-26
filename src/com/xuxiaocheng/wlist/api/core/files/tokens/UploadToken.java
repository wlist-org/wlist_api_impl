package com.xuxiaocheng.wlist.api.core.files.tokens;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * The upload token.
 * @param token internal token.
 */
public record UploadToken(String token) implements Serializable, Recyclable {
    public static void serialize(final UploadToken self, final MessagePacker packer) throws IOException {
        packer.packString(self.token);
    }

    public static UploadToken deserialize(final MessageUnpacker unpacker) throws IOException {
        final String token = unpacker.unpackString();
        return new UploadToken(token);
    }
}
