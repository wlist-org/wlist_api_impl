package com.xuxiaocheng.wlist.api.core.files.confirmations;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.tokens.UploadToken;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * The confirmation to download a file.
 * @param done if true, the file has been successfully downloaded. (Uploaded by Speed transmission.)
 * @param token the upload token.
 */
public record UploadConfirmation(boolean done, UploadToken token)
        implements Serializable, Recyclable {
    public static void serialize(final UploadConfirmation self, final MessagePacker packer) throws IOException {
        packer.packBoolean(self.done);
        UploadToken.serialize(self.token, packer);
    }

    public static UploadConfirmation deserialize(final MessageUnpacker unpacker) throws IOException {
        final boolean done = unpacker.unpackBoolean();
        final UploadToken token = UploadToken.deserialize(unpacker);
        return new UploadConfirmation(done, token);
    }
}
