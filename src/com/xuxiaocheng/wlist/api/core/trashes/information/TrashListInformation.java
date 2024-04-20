package com.xuxiaocheng.wlist.api.core.trashes.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The information list of a trashed file list.
 * @param total the total number of files in the directory.
 * @param filtered the number of files after filtering. (see {@link com.xuxiaocheng.wlist.api.core.files.options.Filter}).
 * @param files the information list.
 */
public record TrashListInformation(long total, long filtered, List<TrashInformation> files)
        implements Serializable, Recyclable {
    public static void serialize(final TrashListInformation self, final MessagePacker packer) throws IOException {
        packer.packLong(self.total);
        packer.packLong(self.filtered);
        packer.packArrayHeader(self.files.size());
        for (final TrashInformation file: self.files)
            TrashInformation.serialize(file, packer);
    }

    public static TrashListInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final long total = unpacker.unpackLong();
        final long filtered = unpacker.unpackLong();
        final int size = unpacker.unpackArrayHeader();
        final List<TrashInformation> files = new ArrayList<>(size);
        for (int i = 0; i < size; ++i)
            files.add(TrashInformation.deserialize(unpacker));
        return new TrashListInformation(total, filtered, files);
    }
}
