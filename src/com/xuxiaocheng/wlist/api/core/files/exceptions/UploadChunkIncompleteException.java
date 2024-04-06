package com.xuxiaocheng.wlist.api.core.files.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown if you call {@link com.xuxiaocheng.wlist.api.core.files.Upload#finish(com.xuxiaocheng.wlist.api.core.CoreClient, com.xuxiaocheng.wlist.api.core.files.tokens.UploadToken)}
 * before all the futures returned by {@link com.xuxiaocheng.wlist.api.core.files.Upload#upload(com.xuxiaocheng.wlist.api.core.CoreClient, com.xuxiaocheng.wlist.api.core.files.tokens.UploadToken, int, java.nio.ByteBuffer)} are complete.
 */
public class UploadChunkIncompleteException extends RuntimeException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 1443852284365190257L;

    /**
     * Internal constructor.
     */
    private UploadChunkIncompleteException() {
        super();
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.UploadChunkIncomplete;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static UploadChunkIncompleteException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new UploadChunkIncompleteException();
    }
}
