package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * The information of each download chunk.
 * @param range support downloads this chunk in parts or not.
 * @param start the start byte index of the entire file.
 * @param size the chunk size.
 */
public record DownloadChunkInformation(boolean range, long start, long size)
        implements Serializable, Recyclable {
    public static void serialize(final DownloadChunkInformation self, final MessagePacker packer) throws IOException {
        packer.packBoolean(self.range);
        packer.packLong(self.start);
        packer.packLong(self.size);
    }

    public static DownloadChunkInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final boolean range = unpacker.unpackBoolean();
        final long start = unpacker.unpackLong();
        final long size = unpacker.unpackLong();
        return new DownloadChunkInformation(range, start, size);
    }
}
