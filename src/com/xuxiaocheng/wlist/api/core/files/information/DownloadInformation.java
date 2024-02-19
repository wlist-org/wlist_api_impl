package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.Main;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * The information of download chunks.
 * You can recalculate the ranges of the chunks to ensure that the chunks cover the entire file that needs to be downloaded.
 * Note that the chunk bounds may overlap.
 * @param chunks the chunks which can be downloaded independently.
 *               <b>The chunk id is the index of the list.</b>
 * @param expire the expiry time.
 */
public record DownloadInformation(List<DownloadChunkInformation> chunks, Instant expire) implements Serializable, AutoCloseable {
    @Override
    public void close() { throw Main.stub(); }
}
