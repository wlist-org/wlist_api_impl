package com.xuxiaocheng.wlist.api.core.files.tokens;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;

/**
 * The refresh token.
 * Note that it will be expired if the refresh is finished/canceled or the server is closed.
 * @param storage the source storage.
 * @param token internal token.
 */
public record RefreshToken(long storage, String token) implements Serializable, Recyclable {
}
