package com.xuxiaocheng.wlist.api.core.files.tokens;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * The refresh token.
 * Note that it will be expired if the refresh is finished/canceled or the server is closed.
 * @param token internal token.
 */
public record RefreshToken(String token) implements Serializable, Recyclable {
    public static void serialize(final RefreshToken self, final MessagePacker packer) throws IOException {
        packer.packString(self.token);
    }

    public static RefreshToken deserialize(final MessageUnpacker unpacker) throws IOException {
        final String token = unpacker.unpackString();
        return new RefreshToken(token);
    }
}
