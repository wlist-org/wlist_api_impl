package com.xuxiaocheng.wlist.api.core.storages.information;

import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.time.Instant;

/**
 * The detail information of a storage.
 * @param basic the basic information.
 * @param size the storage size already used. (-1 means unknown.)
 * @param indexedSize the storage size already indexed.
 * @param totalSize the storage total size, both used and not used (-1 means unknown.)
 * @param uploadFlow the rest upload flow. (-1 means unknown. -2 means infinite.)
 * @param uploadFlowStart the start time for counting the upload flow.
 * @param uploadFlowRefresh the next time when refresh the upload flow.
 * @param downloadFlow the rest download flow. (-1 means unknown. -2 means infinite.)
 * @param downloadFlowStart the start time for counting the download flow.
 * @param downloadFlowRefresh the next time when refresh the download flow.
 * @param maxSizePerFile the max size per file can be uploaded.
 */
public record StorageDetailsInformation(StorageInformation basic, long size, long indexedSize, long totalSize,
                                        long uploadFlow, Instant uploadFlowStart, Instant uploadFlowRefresh,
                                        long downloadFlow, Instant downloadFlowStart, Instant downloadFlowRefresh,
                                        long maxSizePerFile) {
    public static void serialize(final StorageDetailsInformation self, final MessagePacker packer) throws IOException {
        StorageInformation.serialize(self.basic, packer);
        packer.packLong(self.size);
        packer.packLong(self.indexedSize);
        packer.packLong(self.totalSize);
        packer.packLong(self.uploadFlow);
        packer.packTimestamp(self.uploadFlowStart);
        packer.packTimestamp(self.uploadFlowRefresh);
        packer.packLong(self.downloadFlow);
        packer.packTimestamp(self.downloadFlowStart);
        packer.packTimestamp(self.downloadFlowRefresh);
        packer.packLong(self.maxSizePerFile);
    }

    public static StorageDetailsInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final StorageInformation basic = StorageInformation.deserialize(unpacker);
        final long size = unpacker.unpackLong();
        final long indexedSize = unpacker.unpackLong();
        final long totalSize = unpacker.unpackLong();
        final long uploadFlow = unpacker.unpackLong();
        final Instant uploadFlowStart = unpacker.unpackTimestamp();
        final Instant uploadFlowRefresh = unpacker.unpackTimestamp();
        final long downloadFlow = unpacker.unpackLong();
        final Instant downloadFlowStart = unpacker.unpackTimestamp();
        final Instant downloadFlowRefresh = unpacker.unpackTimestamp();
        final long maxSizePerFile = unpacker.unpackLong();
        return new StorageDetailsInformation(
                basic, size, indexedSize, totalSize,
                uploadFlow, uploadFlowStart, uploadFlowRefresh,
                downloadFlow, downloadFlowStart, downloadFlowRefresh,
                maxSizePerFile
        );
    }
}
