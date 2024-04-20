package com.xuxiaocheng.wlist.api.core.trashes.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;

/**
 * The information of a trashed file/directory.
 * @param id the file/directory id.
 * @param name the file/directory name.
 * @param isDirectory whether a directory or a file.
 * @param size the file/directory size. (-1 means unknown.)
 * @param createTime the time when created.
 * @param updateTime the time when updated.
 * @param trashTime the time when trashed.
 */
public record TrashInformation(long id, String name, boolean isDirectory, long size,
                               Instant createTime, Instant updateTime, Instant trashTime)
        implements Serializable, Recyclable {
    public static void serialize(final TrashInformation self, final MessagePacker packer) throws IOException {
        packer.packLong(self.id);
        packer.packString(self.name);
        packer.packBoolean(self.isDirectory);
        packer.packLong(self.size);
        packer.packTimestamp(self.createTime);
        packer.packTimestamp(self.updateTime);
        packer.packTimestamp(self.trashTime);
    }

    public static TrashInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final long id = unpacker.unpackLong();
        final String name = unpacker.unpackString();
        final boolean isDirectory = unpacker.unpackBoolean();
        final long size = unpacker.unpackLong();
        final Instant createTime = unpacker.unpackTimestamp();
        final Instant updateTime = unpacker.unpackTimestamp();
        final Instant trashTime = unpacker.unpackTimestamp();
        return new TrashInformation(id, name, isDirectory, size, createTime, updateTime, trashTime);
    }
}
