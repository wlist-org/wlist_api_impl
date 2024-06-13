package com.xuxiaocheng.wlist.api.impl.functions;

import com.xuxiaocheng.wlist.api.common.either.Either;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileDetailsInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.options.Duplicate;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;
import com.xuxiaocheng.wlist.api.impl.ServerStarter;
import io.netty.buffer.ByteBuf;
import org.msgpack.core.MessageUnpacker;

import java.util.concurrent.CompletableFuture;

public enum File {;
    private static native CompletableFuture<Either<FileListInformation, RefreshConfirmation>> list0(final String id, final String token, final FileLocation directory, final ListFileOptions options);
    public static CompletableFuture<ByteBuf> list(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation directory = FileLocation.deserialize(unpacker);
            final ListFileOptions options = ListFileOptions.deserialize(unpacker);
            return File.list0(id, token, directory, options);
        }, (either, packer) -> {
            if (either.isLeft()) {
                packer.packBoolean(true);
                //noinspection OptionalGetWithoutIsPresent
                FileListInformation.serialize(either.getLeft().get(), packer);
            } else {
                packer.packBoolean(false);
                //noinspection OptionalGetWithoutIsPresent
                RefreshConfirmation.serialize(either.getRight().get(), packer);
            }
        });
    }

    private static native CompletableFuture<FileDetailsInformation> get0(final String id, final String token, final FileLocation file, final boolean check);
    public static CompletableFuture<ByteBuf> get(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation file = FileLocation.deserialize(unpacker);
            final boolean check = unpacker.unpackBoolean();
            return File.get0(id, token, file, check);
        }, FileDetailsInformation::serialize);
    }

    private static native CompletableFuture<Void> checkName0(final String id, final String token, final String name, final FileLocation parent, final boolean isDirectory);
    public static CompletableFuture<ByteBuf> checkName(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final String name = unpacker.unpackString();
            final FileLocation parent = FileLocation.deserialize(unpacker);
            final boolean isDirectory = unpacker.unpackBoolean();
            return File.checkName0(id, token, name, parent, isDirectory);
        }, ServerStarter::serializeVoid);
    }

    private static native CompletableFuture<FileInformation> mkdir0(final String id, final String token, final FileLocation parent, final String name, final Duplicate duplicate);
    public static CompletableFuture<ByteBuf> mkdir(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation parent = FileLocation.deserialize(unpacker);
            final String name = unpacker.unpackString();
            final Duplicate duplicate = Duplicate.valueOf(unpacker.unpackString());
            return File.mkdir0(id, token, parent, name, duplicate);
        }, FileInformation::serialize);
    }

    private static native CompletableFuture<FileInformation> copy0(final String id, final String token, final FileLocation source, final FileLocation target, final String name, final Duplicate duplicate);
    public static CompletableFuture<ByteBuf> copy(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation source = FileLocation.deserialize(unpacker);
            final FileLocation target = FileLocation.deserialize(unpacker);
            final String name = unpacker.unpackString();
            final Duplicate duplicate = Duplicate.valueOf(unpacker.unpackString());
            return File.copy0(id, token, source, target, name, duplicate);
        }, FileInformation::serialize);
    }

    private static native CompletableFuture<FileInformation> move0(final String id, final String token, final FileLocation source, final FileLocation target, final Duplicate duplicate);
    public static CompletableFuture<ByteBuf> move(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation source = FileLocation.deserialize(unpacker);
            final FileLocation target = FileLocation.deserialize(unpacker);
            final Duplicate duplicate = Duplicate.valueOf(unpacker.unpackString());
            return File.move0(id, token, source, target, duplicate);
        }, FileInformation::serialize);
    }

    private static native CompletableFuture<FileInformation> rename0(final String id, final String token, final FileLocation file, final String name, final Duplicate duplicate);
    public static CompletableFuture<ByteBuf> rename(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String token = unpacker.unpackString();
            final FileLocation file = FileLocation.deserialize(unpacker);
            final String name = unpacker.unpackString();
            final Duplicate duplicate = Duplicate.valueOf(unpacker.unpackString());
            return File.rename0(id, token, file, name, duplicate);
        }, FileInformation::serialize);
    }
}
