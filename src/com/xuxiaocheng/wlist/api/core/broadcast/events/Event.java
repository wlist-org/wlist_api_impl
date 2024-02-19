package com.xuxiaocheng.wlist.api.core.broadcast.events;

import com.xuxiaocheng.wlist.api.Main;

import java.io.Serializable;

/**
 * An interface that represents an event.
 */
public sealed interface Event extends Serializable, AutoCloseable permits ServerCloseEvent,
        NetworkSendingEvent, NetworkReceivingEvent,
        StorageAddEvent, StorageRemoveEvent, StorageLoginEvent,
        FileUploadEvent, FileUpdateEvent, FileTrashEvent {
    default void close() { throw Main.stub(); }
}
