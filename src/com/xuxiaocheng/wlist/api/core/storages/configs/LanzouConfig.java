package com.xuxiaocheng.wlist.api.core.storages.configs;

import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;

/**
 * The Lanzou type storage config.
 * @param phoneNumber login phone number.
 * @param password login password.
 * @param rootDirectoryId root directory id. (Default should be -1.)
 */
public record LanzouConfig(String phoneNumber, String password, long rootDirectoryId) implements Config {
    public static void serialize(final LanzouConfig self, final MessagePacker packer) throws IOException {
        packer.packString(self.phoneNumber);
        packer.packString(self.password);
        packer.packLong(self.rootDirectoryId);
    }

    public static LanzouConfig deserialize(final MessageUnpacker unpacker) throws IOException {
        final String phoneNumber = unpacker.unpackString();
        final String password = unpacker.unpackString();
        final long rootDirectoryId = unpacker.unpackLong();
        return new LanzouConfig(phoneNumber, password, rootDirectoryId);
    }
}
