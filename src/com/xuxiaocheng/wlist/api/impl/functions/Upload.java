package com.xuxiaocheng.wlist.api.impl.functions;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.UploadConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.files.options.Duplicate;
import com.xuxiaocheng.wlist.api.core.files.tokens.UploadToken;
import com.xuxiaocheng.wlist.api.impl.ServerStarter;
import com.xuxiaocheng.wlist.api.impl.data.InternalUploadInformation;
import com.xuxiaocheng.wlist.api.impl.data.InternalUploadInformationCallback;
import com.xuxiaocheng.wlist.api.impl.data.UploadInformationExtra;
import io.netty.buffer.ByteBuf;
import org.msgpack.core.MessageUnpacker;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public enum Upload {;
    private static native CompletableFuture<UploadConfirmation> request0(final String id, final String token, final FileLocation parent, final String name, final long size, final String md5, final String[] md5s, final Duplicate duplicate);
    public static CompletableFuture<ByteBuf> request(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation parent = FileLocation.deserialize(unpacker);
            final String name = unpacker.unpackString();
            final long size = unpacker.unpackLong();
            final String md5 = unpacker.unpackString();
            final int md5sLen = unpacker.unpackArrayHeader();
            final String[] md5s = new String[md5sLen];
            for (int i = 0; i < md5sLen; ++i)
                md5s[i] = unpacker.unpackString();
            final Duplicate duplicate = Duplicate.valueOf(unpacker.unpackString());
            return Upload.request0(id, token, parent, name, size, md5, md5s, duplicate);
        }, UploadConfirmation::serialize);
    }

    private static native CompletableFuture<Void> cancel0(final String id, final UploadToken token);
    public static CompletableFuture<ByteBuf> cancel(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final UploadToken token = UploadToken.deserialize(unpacker);
            return Upload.cancel0(id, token);
        }, ServerStarter::serializeVoid);
    }
    public static native CompletableFuture<Void> cancelClient(final UploadToken token);

    private static native CompletableFuture<UploadInformationExtra> confirm0(final String id, final UploadToken token);
    public static CompletableFuture<ByteBuf> confirm(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final UploadToken token = UploadToken.deserialize(unpacker);
            return Upload.confirm0(id, token);
        }, UploadInformationExtra::serialize);
    }
    public static native CompletableFuture<Void> confirmClient(final UploadToken token, final UploadInformationExtra information);


    private static native CompletableFuture<InternalUploadInformation> upload0(final String id, final UploadToken token, final int chunk);
    public static CompletableFuture<ByteBuf> upload(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final UploadToken token = UploadToken.deserialize(unpacker);
            final int chunk = unpacker.unpackInt();
            return Upload.upload0(id, token, chunk);
        }, InternalUploadInformation::serialize);
    }
    public static native CompletableFuture<InternalUploadInformationCallback> uploadClient(final UploadToken token, final InternalUploadInformation information, final ByteBuffer buffer, final AtomicBoolean controller);

    private static native CompletableFuture<Void> uploadCallback0(final String id, final UploadToken token, final int chunk, final InternalUploadInformationCallback callback);
    public static CompletableFuture<ByteBuf> uploadCallback(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final UploadToken token = UploadToken.deserialize(unpacker);
            final int chunk = unpacker.unpackInt();
            final InternalUploadInformationCallback callback = InternalUploadInformationCallback.deserialize(unpacker);
            return Upload.uploadCallback0(id, token, chunk, callback);
        }, ServerStarter::serializeVoid);
    }

    private static native CompletableFuture<FileInformation> finish0(final String id, final UploadToken token);
    public static CompletableFuture<ByteBuf> finish(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final UploadToken token = UploadToken.deserialize(unpacker);
            return Upload.finish0(id, token);
        }, FileInformation::serialize);
    }
}
