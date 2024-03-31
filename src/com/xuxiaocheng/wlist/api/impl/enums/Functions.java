package com.xuxiaocheng.wlist.api.impl.enums;

import com.xuxiaocheng.wlist.api.impl.functions.Client;
import io.netty.buffer.ByteBuf;
import org.msgpack.core.MessageUnpacker;

import java.util.concurrent.CompletableFuture;

public enum Functions {
    Login(Client::login),
    Refresh(Client::refresh),

    // TODO
    ;

    public interface Handler {
        CompletableFuture<ByteBuf> handle(final String id, final MessageUnpacker unpacker);
    }

    private final Handler handler;

    Functions(final Handler handler) {
        this.handler = handler;
    }

    public Handler getHandler() {
        return this.handler;
    }
}
