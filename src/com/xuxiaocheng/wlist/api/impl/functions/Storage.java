package com.xuxiaocheng.wlist.api.impl.functions;

import com.xuxiaocheng.wlist.api.core.storages.information.StorageDetailsInformation;
import com.xuxiaocheng.wlist.api.core.storages.information.StorageListInformation;
import com.xuxiaocheng.wlist.api.core.storages.options.ListStorageOptions;
import com.xuxiaocheng.wlist.api.impl.ServerStarter;
import io.netty.buffer.ByteBuf;
import org.msgpack.core.MessageUnpacker;

import java.util.concurrent.CompletableFuture;

public enum Storage {;
    private static native CompletableFuture<StorageListInformation> list0(final String id, final String token, final ListStorageOptions options);
    public static CompletableFuture<ByteBuf> list(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final ListStorageOptions options = ListStorageOptions.deserialize(unpacker);
            return Storage.list0(id, token, options);
        }, StorageListInformation::serialize);
    }

    private static native CompletableFuture<StorageDetailsInformation> get0(final String id, final String token, final long storage, final boolean check);
    public static CompletableFuture<ByteBuf> get(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final long storage = unpacker.unpackLong();
            final boolean check = unpacker.unpackBoolean();
            return Storage.get0(id, token, storage, check);
        }, StorageDetailsInformation::serialize);
    }

    private static native CompletableFuture<Void> remove0(final String id, final String token, final long storage);
    public static CompletableFuture<ByteBuf> remove(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final long storage = unpacker.unpackLong();
            return Storage.remove0(id, token, storage);
        }, ServerStarter::serializeVoid);
    }

    private static native CompletableFuture<Void> rename0(final String id, final String token, final long storage, final String name);
    public static CompletableFuture<ByteBuf> rename(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final long storage = unpacker.unpackLong();
            final String name = unpacker.unpackString();
            return Storage.rename0(id, token, storage, name);
        }, ServerStarter::serializeVoid);
    }

    private static native CompletableFuture<Void> setReadonly0(final String id, final String token, final long storage, final boolean readonly);
    public static CompletableFuture<ByteBuf> setReadonly(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final long storage = unpacker.unpackLong();
            final boolean readonly = unpacker.unpackBoolean();
            return Storage.setReadonly0(id, token, storage, readonly);
        }, ServerStarter::serializeVoid);
    }
}
