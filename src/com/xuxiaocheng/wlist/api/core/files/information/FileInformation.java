package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;

/**
 * The information of a file/directory.
 * @param id the file/directory id.
 * @param parentId the parent directory id. (For root directory, it equals to id.)
 * @param name the file/directory name.
 * @param isDirectory whether a directory or a file.
 * @param size the file/directory size. (-1 means unknown.)
 * @param createTime the nullable time when created. (null means unknown.)
 * @param updateTime the nullable time when updated. (null means unknown.)
 */
public record FileInformation(long id, long parentId, String name, boolean isDirectory, long size,
                              Instant createTime, Instant updateTime)
        implements Serializable, Recyclable {
    public static void serialize(final FileInformation self, final MessagePacker packer) throws IOException {
        packer.packLong(self.id);
        packer.packLong(self.parentId);
        packer.packString(self.name);
        packer.packBoolean(self.isDirectory);
        packer.packLong(self.size);
        if (self.createTime == null) {
            packer.packBoolean(false);
        } else {
            packer.packBoolean(true);
            packer.packTimestamp(self.createTime);
        }
        if (self.createTime == null) {
            packer.packBoolean(false);
        } else {
            packer.packBoolean(true);
            packer.packTimestamp(self.updateTime);
        }
    }

    public static FileInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final long id = unpacker.unpackLong();
        final long parentId = unpacker.unpackLong();
        final String name = unpacker.unpackString();
        final boolean isDirectory = unpacker.unpackBoolean();
        final long size = unpacker.unpackLong();
        final Instant createTime = unpacker.unpackBoolean() ? unpacker.unpackTimestamp() : null;
        final Instant updateTime = unpacker.unpackBoolean() ? unpacker.unpackTimestamp() : null;
        return new FileInformation(id, parentId, name, isDirectory, size, createTime, updateTime);
    }
}
