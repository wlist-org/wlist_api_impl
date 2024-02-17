package com.xuxiaocheng.wlist.api.core.files.information;

import java.io.Serial;

/**
 * Thrown if you call {@link com.xuxiaocheng.wlist.api.core.files.Upload#finish(com.xuxiaocheng.wlist.api.core.CoreClient, com.xuxiaocheng.wlist.api.core.files.tokens.UploadToken)}
 * before all the futures returned by {@link com.xuxiaocheng.wlist.api.core.files.Upload#upload(com.xuxiaocheng.wlist.api.core.CoreClient, com.xuxiaocheng.wlist.api.core.files.tokens.UploadToken, int, java.nio.ByteBuffer)} are complete.
 */
public class UploadChunkIncompleteException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1443852284365190257L;

    /**
     * Internal constructor.
     */
    public UploadChunkIncompleteException() {
        super();
    }
}
