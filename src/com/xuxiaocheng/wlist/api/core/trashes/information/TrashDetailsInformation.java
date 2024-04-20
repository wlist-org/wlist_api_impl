package com.xuxiaocheng.wlist.api.core.trashes.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * The detail information of a trashed file/directory.
 * @param basic the basic information.
 * @param optionalMd5 the optional(<b>nullable</b>) md5. (This is a lowercase string with a length of 32.) (For directory, it's always null.)
 */
public record TrashDetailsInformation(TrashInformation basic, String optionalMd5)
        implements Serializable, Recyclable {
    public static void serialize(final TrashDetailsInformation self, final MessagePacker packer) throws IOException {
        TrashInformation.serialize(self.basic, packer);
        if (self.optionalMd5 == null) {
            packer.packBoolean(false);
        } else {
            packer.packBoolean(true);
            packer.packString(self.optionalMd5);
        }
    }

    public static TrashDetailsInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final TrashInformation basic = TrashInformation.deserialize(unpacker);
        final String optionalMd5 = unpacker.unpackBoolean() ? unpacker.unpackString() : null;
        return new TrashDetailsInformation(basic, optionalMd5);
    }
}
