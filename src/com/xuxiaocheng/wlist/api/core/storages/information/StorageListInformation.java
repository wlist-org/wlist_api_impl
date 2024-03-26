package com.xuxiaocheng.wlist.api.core.storages.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The information list of a storage list.
 * @param total the total number of storages in the directory.
 * @param filtered the number of storages after filtering. (see {@link com.xuxiaocheng.wlist.api.core.storages.options.Filter}).
 * @param storages the information list.
 */
public record StorageListInformation(long total, long filtered, List<StorageInformation> storages)
        implements Serializable, Recyclable {
    public static void serialize(final StorageListInformation self, final MessagePacker packer) throws IOException {
        packer.packLong(self.total);
        packer.packLong(self.filtered);
        packer.packArrayHeader(self.storages.size());
        for (final StorageInformation storage: self.storages)
            StorageInformation.serialize(storage, packer);
    }

    public static StorageListInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final long total = unpacker.unpackLong();
        final long filtered = unpacker.unpackLong();
        final int size = unpacker.unpackArrayHeader();
        final List<StorageInformation> storages = new ArrayList<>(size);
        for (int i = 0; i < size; ++i)
            storages.add(StorageInformation.deserialize(unpacker));
        return new StorageListInformation(total, filtered, storages);
    }
}
