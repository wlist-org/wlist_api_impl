package com.xuxiaocheng.wlist.api.core.broadcast.events;

import java.io.Serializable;

/**
 * An interface that represents an event.
 */
public sealed interface Event extends Serializable permits ServerCloseEvent,
        StorageAddEvent, StorageRemoveEvent, StorageLoginEvent {
}
