package com.xuxiaocheng.wlist.api.core.files.tokens;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * The upload token.
 * Note that it will be expired if the upload is finished/canceled or the server is closed.
 * @param storage the source storage.
 * @param token internal token.
 */
public record UploadToken(long storage, String token) implements Serializable, Recyclable {
    public static void serialize(final UploadToken self, final MessagePacker packer) throws IOException {
        packer.packLong(self.storage);
        packer.packString(self.token);
    }

    public static UploadToken deserialize(final MessageUnpacker unpacker) throws IOException {
        final long storage = unpacker.unpackLong();
        final String token = unpacker.unpackString();
        return new UploadToken(storage, token);
    }
}
