package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;

/**
 * The information of each upload chunk.
 * @param start the start byte index of the entire file.
 * @param size the chunk size.
 */
public record UploadChunkInformation(long start, long size)
        implements Serializable, Recyclable {
}
