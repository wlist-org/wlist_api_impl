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
 * The information of download chunks.
 * You can recalculate the ranges of the chunks to ensure that the chunks cover the entire file that needs to be downloaded.
 * Note that the chunk bounds may overlap.
 * @param chunks the downloaded chunks.<b>The chunk id is the index of the list.</b>
 * @param expire the expiry time.
 */
public record DownloadInformation(List<DownloadChunkInformation> chunks, Instant expire)
        implements Serializable, Recyclable {
    public static void serialize(final DownloadInformation self, final MessagePacker packer) throws IOException {
        packer.packArrayHeader(self.chunks.size());
        for (final DownloadChunkInformation chunk: self.chunks)
            DownloadChunkInformation.serialize(chunk, packer);
        packer.packTimestamp(self.expire);
    }

    public static DownloadInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final int size = unpacker.unpackArrayHeader();
        final List<DownloadChunkInformation> chunks = new ArrayList<>(size);
        for (int i = 0; i < size; ++i)
            chunks.add(DownloadChunkInformation.deserialize(unpacker));
        final Instant expire = unpacker.unpackTimestamp();
        return new DownloadInformation(chunks, expire);
    }
}
