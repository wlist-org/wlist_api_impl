package com.xuxiaocheng.wlist.api.impl.enums;

import com.xuxiaocheng.wlist.api.impl.functions.Client;
import com.xuxiaocheng.wlist.api.impl.functions.File;
import com.xuxiaocheng.wlist.api.impl.functions.Refresh;
import com.xuxiaocheng.wlist.api.impl.functions.Storage;
import com.xuxiaocheng.wlist.api.impl.functions.types.Lanzou;
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

    StorageLanzouAdd(Lanzou::add),
    StorageLanzouUpdate(Lanzou::update),
    StorageLanzouCheckConfig(Lanzou::checkConfig),

    FileList(File::list),
    FileGet(File::get),
    FileCheckName(File::checkName),
    FileMkdir(File::mkdir),
    FileCopy(File::copy),
    FileMove(File::move),
    FileRename(File::rename),

    RefreshRequest(Refresh::refresh),
    RefreshCancel(Refresh::cancel),
    RefreshConfirm(Refresh::confirm),
    RefreshPause(Refresh::pause),
    RefreshResume(Refresh::resume),
    RefreshIsPaused(Refresh::isPaused),
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
