package com.xuxiaocheng.wlist.api.core.files.information;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * The information of download chunks.
 * You can recalculate the ranges of the chunks to ensure that the chunks cover the entire file that needs to be downloaded.
 * Note that the chunk bounds won't overlap.
 * @param chunks the chunks which can be downloaded parallel.
 *               the chunk id is the index of the entry.
 * @param expire the expiry time.
 */
public record DownloadInformation(List<DownloadChunkInformation> chunks, Instant expire) {
}
