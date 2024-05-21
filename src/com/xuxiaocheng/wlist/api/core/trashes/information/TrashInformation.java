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
 * @param createTime the nullable time when created. (null means unknown.)
 * @param updateTime the nullable time when updated. (null means unknown.)
 * @param trashTime the nullable time when trashed. (null means unknown.)
 */
public record TrashInformation(long id, String name, boolean isDirectory, long size,
                               Instant createTime, Instant updateTime, Instant trashTime)
        implements Serializable, Recyclable {
    public static void serialize(final TrashInformation self, final MessagePacker packer) throws IOException {
        packer.packLong(self.id);
        packer.packString(self.name);
        packer.packBoolean(self.isDirectory);
        packer.packLong(self.size);
        if (self.createTime == null) packer.packBoolean(false);
        else packer.packBoolean(true).packTimestamp(self.createTime);
        if (self.createTime == null) packer.packBoolean(false);
        else packer.packBoolean(true).packTimestamp(self.updateTime);
        if (self.trashTime == null) packer.packBoolean(false);
        else packer.packBoolean(true).packTimestamp(self.trashTime);
    }

    public static TrashInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final long id = unpacker.unpackLong();
        final String name = unpacker.unpackString();
        final boolean isDirectory = unpacker.unpackBoolean();
        final long size = unpacker.unpackLong();
        final Instant createTime = unpacker.unpackBoolean() ? unpacker.unpackTimestamp() : null;
        final Instant updateTime = unpacker.unpackBoolean() ? unpacker.unpackTimestamp() : null;
        final Instant trashTime = unpacker.unpackBoolean() ? unpacker.unpackTimestamp() : null;
        return new TrashInformation(id, name, isDirectory, size, createTime, updateTime, trashTime);
    }
}
