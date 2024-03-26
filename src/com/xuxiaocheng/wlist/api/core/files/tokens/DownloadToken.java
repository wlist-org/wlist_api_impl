package com.xuxiaocheng.wlist.api.core.files.tokens;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * The download token.
 * @param token internal token.
 */
public record DownloadToken(String token) implements Serializable, Recyclable {
    public static void serialize(final DownloadToken self, final MessagePacker packer) throws IOException {
        packer.packString(self.token);
    }

    public static DownloadToken deserialize(final MessageUnpacker unpacker) throws IOException {
        final String token = unpacker.unpackString();
        return new DownloadToken(token);
    }
}
