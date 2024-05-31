package com.xuxiaocheng.wlist.api.core.storages.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.storages.types.StorageType;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;

/**
 * The information of a storage.
 * @param id the id of the storage.
 * @param name the name of the storage.
 * @param readOnly true if the storage is read-only. (Always true if the type is shared.)
 * @param type the type of the storage.
 * @param available true if the storage is available. (The share link may be canceled or the personal account may be deleted.)
 * @param createTime the time when created.
 * @param updateTime the time when updated.
 * @param rootDirectoryId the root directory id of the storage.
 */
public record StorageInformation(long id, String name, boolean readOnly, StorageType<?> type, boolean available,
                                 Instant createTime, Instant updateTime, long rootDirectoryId)
        implements Serializable, Recyclable {
    public static void serialize(final StorageInformation self, final MessagePacker packer) throws IOException {
        packer.packLong(self.id);
        packer.packString(self.name);
        packer.packBoolean(self.readOnly);
        packer.packString(StorageType.name(self.type));
        packer.packBoolean(self.available);
        packer.packTimestamp(self.createTime);
        packer.packTimestamp(self.updateTime);
        packer.packLong(self.rootDirectoryId);
    }

    public static StorageInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final long id = unpacker.unpackLong();
        final String name = unpacker.unpackString();
        final boolean readOnly = unpacker.unpackBoolean();
        final StorageType type = StorageType.instanceOf(unpacker.unpackString());
        final boolean available = unpacker.unpackBoolean();
        final Instant createTime = unpacker.unpackTimestamp();
        final Instant updateTime = unpacker.unpackTimestamp();
        final long rootDirectoryId = unpacker.unpackLong();
        return new StorageInformation(id, name, readOnly, type, available, createTime, updateTime, rootDirectoryId);
    }
}
