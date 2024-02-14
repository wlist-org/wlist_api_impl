package com.xuxiaocheng.wlist.api.core.files.information;

/**
 * The information of each download chunk.
 * @param start the start byte index of the total file.
 * @param size the chunk size.
 */
public record DownloadChunkInformation(long start, long size) {
}
