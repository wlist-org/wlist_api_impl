package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;

/**
 * The share information of the files/directories.
 * @param id the sharing id.
 * @param optionalPassword the optional(<b>nullable</b>) password.
 * @param expire the expiry time.
 */
public record ShareInformation(String id, String optionalPassword, Instant expire)
        implements Serializable, Recyclable {
    public static void serialize(final ShareInformation self, final MessagePacker packer) throws IOException {
        packer.packString(self.id);
        if (self.optionalPassword == null)
            packer.packBoolean(false);
        else {
            packer.packBoolean(true);
            packer.packString(self.optionalPassword);
        }
        packer.packTimestamp(self.expire);
    }

    public static ShareInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final String id = unpacker.unpackString();
        final String optionalPassword = unpacker.unpackBoolean() ? unpacker.unpackString() : null;
        final Instant expire = unpacker.unpackTimestamp();
        return new ShareInformation(id, optionalPassword, expire);
    }
}
