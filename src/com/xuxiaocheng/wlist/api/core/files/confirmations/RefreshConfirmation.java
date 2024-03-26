package com.xuxiaocheng.wlist.api.core.files.confirmations;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.tokens.RefreshToken;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * The confirmation to refresh a directory.
 * @param files the count of files in the directory. (-1 means unknown.)
 * @param directories the count of directories in the directory. (-1 means unknown.)
 * @param token the refresh token.
 */
public record RefreshConfirmation(long files, long directories, RefreshToken token)
        implements Serializable, Recyclable {
    public static void serialize(final RefreshConfirmation self, final MessagePacker packer) throws IOException {
        packer.packLong(self.files);
        packer.packLong(self.directories);
        RefreshToken.serialize(self.token, packer);
    }

    public static RefreshConfirmation deserialize(final MessageUnpacker unpacker) throws IOException {
        final long files = unpacker.unpackLong();
        final long directories = unpacker.unpackLong();
        final RefreshToken token = RefreshToken.deserialize(unpacker);
        return new RefreshConfirmation(files, directories, token);
    }
}
