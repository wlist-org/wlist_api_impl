package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * The information of upload chunks.
 * Note that the chunks may not cover the entire file.
 * @param chunks the chunks which can be uploaded independently.
 *               <b>The chunk id is the index of the list.</b>
 * @param expire the expiry time.
 */
public record UploadInformation(List<UploadChunkInformation> chunks, Instant expire)
        implements Serializable, Recyclable {
    public static void serialize(final UploadInformation self, final MessagePacker packer) throws IOException {
        packer.packArrayHeader(self.chunks.size());
        for (final UploadChunkInformation chunk: self.chunks)
            UploadChunkInformation.serialize(chunk, packer);
        packer.packTimestamp(self.expire);
    }

    public static UploadInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final int size = unpacker.unpackArrayHeader();
        final List<UploadChunkInformation> chunks = new ArrayList<>(size);
        for (int i = 0; i < size; ++i)
            chunks.add(UploadChunkInformation.deserialize(unpacker));
        final Instant expire = unpacker.unpackTimestamp();
        return new UploadInformation(chunks, expire);
    }
}
