package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * The information of each upload chunk.
 * @param start the start byte index of the entire file.
 * @param size the chunk size.
 */
public record UploadChunkInformation(long start, long size)
        implements Serializable, Recyclable {
    public static void serialize(final UploadChunkInformation self, final MessagePacker packer) throws IOException {
        packer.packLong(self.start);
        packer.packLong(self.size);
    }

    public static UploadChunkInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final long start = unpacker.unpackLong();
        final long size = unpacker.unpackLong();
        return new UploadChunkInformation(start, size);
    }
}
