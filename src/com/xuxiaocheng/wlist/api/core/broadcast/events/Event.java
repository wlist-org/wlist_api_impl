package com.xuxiaocheng.wlist.api.core.broadcast.events;

/**
 * An interface that represents an event.
 */
public sealed interface Event permits ServerCloseEvent,
        StorageAddEvent, StorageRemoveEvent {
}
