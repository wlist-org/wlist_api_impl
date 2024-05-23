package com.xuxiaocheng.wlist.api.impl.enums;

import com.xuxiaocheng.wlist.api.impl.functions.Client;
import com.xuxiaocheng.wlist.api.impl.functions.Refresh;
import com.xuxiaocheng.wlist.api.impl.functions.Storage;
import io.netty.buffer.ByteBuf;
import org.msgpack.core.MessageUnpacker;

import java.util.concurrent.CompletableFuture;

public enum Functions {
    ClientLogin(Client::login),
    ClientRefresh(Client::refresh),

    StorageList(Storage::list),
    StorageGet(Storage::get),
    StorageRemove(Storage::remove),
    StorageRename(Storage::rename),
    StorageSetReadonly(Storage::setReadonly),

    RefreshRequest(Refresh::refresh),
    RefreshCancel(Refresh::cancel),
    RefreshConfirm(Refresh::confirm),
    RefreshPause(Refresh::pause),
    RefreshResume(Refresh::resume),
    RefreshProgress(Refresh::progress),
    RefreshCheck(Refresh::check),

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
