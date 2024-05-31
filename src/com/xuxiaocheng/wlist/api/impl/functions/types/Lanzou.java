package com.xuxiaocheng.wlist.api.impl.functions.types;

import com.xuxiaocheng.wlist.api.core.storages.configs.LanzouConfig;
import com.xuxiaocheng.wlist.api.core.storages.information.StorageInformation;
import com.xuxiaocheng.wlist.api.impl.ServerStarter;
import io.netty.buffer.ByteBuf;
import org.msgpack.core.MessageUnpacker;

import java.util.concurrent.CompletableFuture;

public enum Lanzou {;
    private static native CompletableFuture<StorageInformation> add0(final String id, final String token, final String storage, final LanzouConfig config);
    public static CompletableFuture<ByteBuf> add(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final String storage = unpacker.unpackString();
            final LanzouConfig config = LanzouConfig.deserialize(unpacker);
            return Lanzou.add0(id, token, storage, config);
        }, StorageInformation::serialize);
    }

    private static native CompletableFuture<Void> update0(final String id, final String token, final long storage, final LanzouConfig config);
    public static CompletableFuture<ByteBuf> update(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final long storage = unpacker.unpackLong();
            final LanzouConfig config = LanzouConfig.deserialize(unpacker);
            return Lanzou.update0(id, token, storage, config);
        }, ServerStarter::serializeVoid);
    }

    private static native CompletableFuture<Void> checkConfig0(final String id, final String token, final LanzouConfig config);
    public static CompletableFuture<ByteBuf> checkConfig(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final LanzouConfig config = LanzouConfig.deserialize(unpacker);
            return Lanzou.checkConfig0(id, token, config);
        }, ServerStarter::serializeVoid);
    }
}
