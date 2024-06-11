package com.xuxiaocheng.wlist.api.impl.functions;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.progresses.RefreshProgress;
import com.xuxiaocheng.wlist.api.core.files.tokens.RefreshToken;
import com.xuxiaocheng.wlist.api.impl.ServerStarter;
import io.netty.buffer.ByteBuf;
import org.msgpack.core.MessageUnpacker;

import java.util.concurrent.CompletableFuture;

public enum Refresh {;
    private static native CompletableFuture<RefreshConfirmation> refresh0(final String id, final String token, final FileLocation directory);
    public static CompletableFuture<ByteBuf> refresh(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation directory = FileLocation.deserialize(unpacker);
            return Refresh.refresh0(id, token, directory);
        }, RefreshConfirmation::serialize);
    }

    private static native CompletableFuture<Void> cancel0(final String id, final RefreshToken token);
    public static CompletableFuture<ByteBuf> cancel(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final RefreshToken token = RefreshToken.deserialize(unpacker);
            return Refresh.cancel0(id, token);
        }, ServerStarter::serializeVoid);
    }

    private static native CompletableFuture<Void> confirm0(final String id, final RefreshToken token);
    public static CompletableFuture<ByteBuf> confirm(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final RefreshToken token = RefreshToken.deserialize(unpacker);
            return Refresh.confirm0(id, token);
        }, ServerStarter::serializeVoid);
    }

    private static native CompletableFuture<Void> pause0(final String id, final RefreshToken token);
    public static CompletableFuture<ByteBuf> pause(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final RefreshToken token = RefreshToken.deserialize(unpacker);
            return Refresh.pause0(id, token);
        }, ServerStarter::serializeVoid);
    }

    private static native CompletableFuture<Void> resume0(final String id, final RefreshToken token);
    public static CompletableFuture<ByteBuf> resume(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final RefreshToken token = RefreshToken.deserialize(unpacker);
            return Refresh.resume0(id, token);
        }, ServerStarter::serializeVoid);
    }

    private static native CompletableFuture<Boolean> isPaused0(final String id, final RefreshToken token);
    public static CompletableFuture<ByteBuf> isPaused(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final RefreshToken token = RefreshToken.deserialize(unpacker);
            return Refresh.isPaused0(id, token);
        }, (b, packer) -> packer.packBoolean(b));
    }

    private static native CompletableFuture<RefreshProgress> progress0(final String id, final RefreshToken token);
    public static CompletableFuture<ByteBuf> progress(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final RefreshToken token = RefreshToken.deserialize(unpacker);
            return Refresh.progress0(id, token);
        }, RefreshProgress::serialize);
    }

    private static native CompletableFuture<Boolean> check0(final String id, final RefreshToken token);
    public static CompletableFuture<ByteBuf> check(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final RefreshToken token = RefreshToken.deserialize(unpacker);
            return Refresh.check0(id, token);
        }, (b, packer) -> packer.packBoolean(b));
    }
}
