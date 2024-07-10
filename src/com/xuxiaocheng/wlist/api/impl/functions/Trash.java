package com.xuxiaocheng.wlist.api.impl.functions;

import com.xuxiaocheng.wlist.api.common.either.Either;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashDetailsInformation;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashInformation;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashListInformation;
import com.xuxiaocheng.wlist.api.core.trashes.options.ListTrashOptions;
import com.xuxiaocheng.wlist.api.impl.ServerStarter;
import io.netty.buffer.ByteBuf;
import org.msgpack.core.MessageUnpacker;

import java.util.concurrent.CompletableFuture;

public enum Trash {;
    private static native CompletableFuture<Either<TrashListInformation, RefreshConfirmation>> list0(final String id, final String token, final ListTrashOptions options);
    public static CompletableFuture<ByteBuf> list(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final ListTrashOptions options = ListTrashOptions.deserialize(unpacker);
            return Trash.list0(id, token, options);
        }, (either, packer) -> {
            if (either.isLeft()) {
                packer.packBoolean(true);
                //noinspection OptionalGetWithoutIsPresent
                TrashListInformation.serialize(either.getLeft().get(), packer);
            } else {
                packer.packBoolean(false);
                //noinspection OptionalGetWithoutIsPresent
                RefreshConfirmation.serialize(either.getRight().get(), packer);
            }
        });
    }

    private static native CompletableFuture<RefreshConfirmation> refresh0(final String id, final String token);
    public static CompletableFuture<ByteBuf> refresh(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            return Trash.refresh0(id, token);
        }, RefreshConfirmation::serialize);
    }

    private static native CompletableFuture<TrashDetailsInformation> get0(final String id, final String token, final FileLocation file, final boolean check);
    public static CompletableFuture<ByteBuf> get(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation file = FileLocation.deserialize(unpacker);
            final boolean check = unpacker.unpackBoolean();
            return Trash.get0(id, token, file, check);
        }, TrashDetailsInformation::serialize);
    }

    private static native CompletableFuture<TrashInformation> trash0(final String id, final String token, final FileLocation file);
    public static CompletableFuture<ByteBuf> trash(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation file = FileLocation.deserialize(unpacker);
            return Trash.trash0(id, token, file);
        }, TrashInformation::serialize);
    }

    private static native CompletableFuture<FileInformation> restore0(final String id, final String token, final FileLocation file, final FileLocation parent);
    public static CompletableFuture<ByteBuf> restore(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation file = FileLocation.deserialize(unpacker);
            final FileLocation parent = FileLocation.deserialize(unpacker);
            return Trash.restore0(id, token, file, parent);
        }, FileInformation::serialize);
    }

    private static native CompletableFuture<Void> delete0(final String id, final String token, final FileLocation file);
    public static CompletableFuture<ByteBuf> delete(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation file = FileLocation.deserialize(unpacker);
            return Trash.delete0(id, token, file);
        }, ServerStarter::serializeVoid);
    }

    private static native CompletableFuture<Void> deleteAll0(final String id, final String token);
    public static CompletableFuture<ByteBuf> deleteAll(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            return Trash.deleteAll0(id, token);
        }, ServerStarter::serializeVoid);
    }
}
