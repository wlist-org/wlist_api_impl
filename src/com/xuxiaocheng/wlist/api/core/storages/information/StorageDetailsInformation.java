package com.xuxiaocheng.wlist.api.core.storages.information;

import java.time.Duration;

/**
 * The detail information of a storage.
 * @param basic the basic information.
 * @param size the storage size already used. (-1 means unknown.)
 * @param indexedSize the storage size already indexed.
 * @param totalSize the storage total size. (-1 means unknown.)
 * @param uploadFlow the rest upload flow. (-1 means unknown.)
 * @param uploadFlowRefresh the duration before next time the upload flow be refreshed. (zero means the uploadFlow is infinite.)
 * @param downloadFlow the rest download flow. (-1 means unknown.)
 * @param downloadFlowRefresh the duration before next time the download flow be refreshed. (zero means the downloadFlow is infinite.)
 * @param maxSizePerFile the max size per file can be uploaded.
 */
public record StorageDetailsInformation(StorageInformation basic, long size, long indexedSize, long totalSize,
                                        long uploadFlow, Duration uploadFlowRefresh, long downloadFlow, Duration downloadFlowRefresh,
                                        long maxSizePerFile) {
}
