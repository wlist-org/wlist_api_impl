package com.xuxiaocheng.wlist.api.core.files.tokens;

import com.xuxiaocheng.wlist.api.Main;

import java.io.Serializable;

/**
 * The upload token.
 * @param token internal token.
 */
public record UploadToken(String token) implements Serializable, AutoCloseable {
    @Override
    public void close() { throw Main.stub(); }
}
