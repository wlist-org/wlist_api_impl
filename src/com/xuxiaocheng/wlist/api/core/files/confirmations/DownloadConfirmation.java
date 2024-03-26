package com.xuxiaocheng.wlist.api.core.files.confirmations;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.tokens.DownloadToken;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * The confirmation to download a file.
 * @param size the real total download size. (Associate with the from/to parameters in {@link com.xuxiaocheng.wlist.api.core.files.Download#request(com.xuxiaocheng.wlist.api.core.CoreClient, String, com.xuxiaocheng.wlist.api.core.files.FileLocation, long, long)}.)
 * @param token the download token.
 */
public record DownloadConfirmation(long size, DownloadToken token)
        implements Serializable, Recyclable {
    public static void serialize(final DownloadConfirmation self, final MessagePacker packer) throws IOException {
        packer.packLong(self.size);
        DownloadToken.serialize(self.token, packer);
    }

    public static DownloadConfirmation deserialize(final MessageUnpacker unpacker) throws IOException {
        final long size = unpacker.unpackLong();
        final DownloadToken token = DownloadToken.deserialize(unpacker);
        return new DownloadConfirmation(size, token);
    }
}
