package com.xuxiaocheng.wlist.api.core.files.tokens;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;

/**
 * The download token.
 * Note that it will be expired if the download is finished/canceled or the server is closed.
 * @param storage the source storage.
 * @param token internal token.
 */
public record DownloadToken(long storage, String token) implements Serializable, Recyclable {
}
