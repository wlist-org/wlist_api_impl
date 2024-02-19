package com.xuxiaocheng.wlist.api.core.files.confirmations;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.files.tokens.UploadToken;

/**
 * The confirmation to download a file.
 * @param done if true, the file has been successfully downloaded. (Uploaded by Speed transmission.)
 * @param token the upload token.
 */
public record UploadConfirmation(boolean done, UploadToken token) implements AutoCloseable {
    @Override
    public void close() { throw Main.stub(); }
}
