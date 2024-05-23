package com.xuxiaocheng.wlist.api.core.files.tokens;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;

/**
 * The upload token.
 * Note that it will be expired if the upload is finished/canceled or the server is closed.
 * @param storage the source storage.
 * @param token internal token.
 */
public record UploadToken(long storage, String token) implements Serializable, Recyclable {
}
