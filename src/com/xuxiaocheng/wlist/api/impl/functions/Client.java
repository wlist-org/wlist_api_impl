package com.xuxiaocheng.wlist.api.impl.functions;

import com.xuxiaocheng.wlist.api.impl.ServerStarter;
import io.netty.buffer.ByteBuf;
import org.msgpack.core.MessageUnpacker;

import java.util.concurrent.CompletableFuture;

public enum Client {;
    private static native CompletableFuture<String> login0(final String id, final String username, final String password);
    public static CompletableFuture<ByteBuf> login(final String id, final MessageUnpacker unpacker) {
        return ServerStarter.server(() -> {
            final String username = unpacker.unpackString();
            final String password = unpacker.unpackString();
            return Client.login0(id, username, password);
        }, (token, packer) -> packer.packString(token));
    }

}
