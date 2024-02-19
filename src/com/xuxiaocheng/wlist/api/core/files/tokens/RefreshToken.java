package com.xuxiaocheng.wlist.api.core.files.tokens;

import com.xuxiaocheng.wlist.api.Main;

import java.io.Serializable;

/**
 * The refresh token.
 * Note that it will be expired if the refresh is finished/canceled or the server is closed.
 * @param token internal token.
 */
public record RefreshToken(String token) implements Serializable, AutoCloseable {
    @Override
    public void close() { throw Main.stub(); }
}
