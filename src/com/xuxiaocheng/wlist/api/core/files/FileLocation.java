package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * A record that locates a file/directory.
 * @param storage the id of the storage.
 * @param fileId the file/directory id.
 * @param isDirectory true if the location is a directory.
 */
public record FileLocation(long storage, long fileId, boolean isDirectory)
        implements Serializable, Recyclable {
    public static void serialize(final FileLocation self, final MessagePacker packer) throws IOException {
        packer.packLong(self.storage);
        packer.packLong(self.fileId);
        packer.packBoolean(self.isDirectory);
    }

    public static FileLocation deserialize(final MessageUnpacker unpacker) throws IOException {
        final long storage = unpacker.unpackLong();
        final long fileId = unpacker.unpackLong();
        final boolean isDirectory = unpacker.unpackBoolean();
        return new FileLocation(storage, fileId, isDirectory);
    }
}
