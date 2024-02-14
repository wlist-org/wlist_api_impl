package com.xuxiaocheng.wlist.api.core.files.tokens;

import java.io.Serializable;

/**
 * The refresh token.
 * @param token internal token.
 */
public record RefreshToken(String token) implements Serializable {
}
