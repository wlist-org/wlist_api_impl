package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.Main;

import java.io.Serializable;

/**
 * The information of each download chunk.
 * @param range support downloads this chunk in parts or not.
 * @param start the start byte index of the entire file.
 * @param size the chunk size.
 */
public record DownloadChunkInformation(boolean range, long start, long size) implements Serializable, AutoCloseable {
    @Override
    public void close() { throw Main.stub(); }
}
