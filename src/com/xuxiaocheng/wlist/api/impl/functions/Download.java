package com.xuxiaocheng.wlist.api.impl.functions;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.DownloadConfirmation;
import com.xuxiaocheng.wlist.api.core.files.tokens.DownloadToken;
import com.xuxiaocheng.wlist.api.impl.ServerStarter;
import com.xuxiaocheng.wlist.api.impl.data.DownloadInformationExtra;
import com.xuxiaocheng.wlist.api.impl.data.InternalDownloadInformation;
import com.xuxiaocheng.wlist.api.impl.data.InternalDownloadInformationCallback;
import io.netty.buffer.ByteBuf;
import org.msgpack.core.MessageUnpacker;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public enum Download {;
    private static native CompletableFuture<DownloadConfirmation> request0(final String id, final String token, final FileLocation file, final long from, final long to);
    public static CompletableFuture<ByteBuf> request(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation file = FileLocation.deserialize(unpacker);
            final long from = unpacker.unpackLong();
            final long to = unpacker.unpackLong();
            return Download.request0(id, token, file, from, to);
        }, DownloadConfirmation::serialize);
    }

    private static native CompletableFuture<Void> cancel0(final String id, final DownloadToken token);
    public static CompletableFuture<ByteBuf> cancel(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final DownloadToken token = DownloadToken.deserialize(unpacker);
            return Download.cancel0(id, token);
        }, ServerStarter::serializeVoid);
    }
    public static native CompletableFuture<Void> cancelClient(final DownloadToken token);

    private static native CompletableFuture<DownloadInformationExtra> confirm0(final String id, final DownloadToken token);
    public static CompletableFuture<ByteBuf> confirm(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final DownloadToken token = DownloadToken.deserialize(unpacker);
            return Download.confirm0(id, token);
        }, DownloadInformationExtra::serialize);
    }
    public static native CompletableFuture<Void> confirmClient(final DownloadToken token, final DownloadInformationExtra information);

    private static native CompletableFuture<InternalDownloadInformation> download0(final String id, final DownloadToken token, final int chunk, final long start, final long len);
    public static CompletableFuture<ByteBuf> download(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final DownloadToken token = DownloadToken.deserialize(unpacker);
            final int chunk = unpacker.unpackInt();
            final long start = unpacker.unpackLong();
            final long len = unpacker.unpackLong();
            return Download.download0(id, token, chunk, start, len);
        }, InternalDownloadInformation::serialize);
    }
    public static native CompletableFuture<InternalDownloadInformationCallback> downloadClient(final DownloadToken token, final InternalDownloadInformation information, final ByteBuffer buffer, final AtomicBoolean controller);

    private static native CompletableFuture<Void> downloadCallback0(final String id, final DownloadToken token, final int chunk, final InternalDownloadInformationCallback callback);
    public static CompletableFuture<ByteBuf> downloadCallback(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final DownloadToken token = DownloadToken.deserialize(unpacker);
            final int chunk = unpacker.unpackInt();
            final InternalDownloadInformationCallback callback = InternalDownloadInformationCallback.deserialize(unpacker);
            return Download.downloadCallback0(id, token, chunk, callback);
        }, ServerStarter::serializeVoid);
    }

    private static native CompletableFuture<Void> finish0(final String id, final DownloadToken token);
    public static CompletableFuture<ByteBuf> finish(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final DownloadToken token = DownloadToken.deserialize(unpacker);
            return Download.finish0(id, token);
        }, ServerStarter::serializeVoid);
    }
}
