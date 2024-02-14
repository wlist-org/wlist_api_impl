package com.xuxiaocheng.wlist.api.core.files.tokens;

import java.io.Serializable;

/**
 * The refresh token.
 * Note that it will be expired if the refresh is finished/canceled or the server is closed.
 * @param token internal token.
 */
public record RefreshToken(String token) implements Serializable {
}
