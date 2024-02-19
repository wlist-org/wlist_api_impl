package com.xuxiaocheng.wlist.api.core.broadcast.events;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;

/**
 * An interface that represents an event.
 */
public sealed interface Event extends Serializable, Recyclable permits ServerCloseEvent,
        NetworkSendingEvent, NetworkReceivingEvent,
        StorageAddEvent, StorageRemoveEvent, StorageLoginEvent,
        FileUploadEvent, FileUpdateEvent, FileTrashEvent {
}
