package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * The information of upload chunks.
 * Note that the chunks may not cover the entire file.
 * @param chunks the chunks which can be uploaded independently.
 *               <b>The chunk id is the index of the list.</b>
 * @param expire the expiry time.
 */
public record UploadInformation(List<UploadChunkInformation> chunks, Instant expire)
        implements Serializable, Recyclable {
}
