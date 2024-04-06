package com.xuxiaocheng.wlist.api.core.storages.information;

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
}
