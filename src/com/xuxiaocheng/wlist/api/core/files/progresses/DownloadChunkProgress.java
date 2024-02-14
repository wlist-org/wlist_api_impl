package com.xuxiaocheng.wlist.api.core.files.progresses;

/**
 * The progress of download chunk.
 * @param downloaded the downloaded bytes of the entire chunk. (The total size of the chunk has returned in {@link com.xuxiaocheng.wlist.api.core.files.information.DownloadChunkInformation})
 */
public record DownloadChunkProgress(long downloaded) {
}
