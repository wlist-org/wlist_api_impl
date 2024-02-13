package com.xuxiaocheng.wlist.api.core.broadcast.events;

/**
 * An event that informs that a new storage has been removed.
 * @param name the name of the removed storage.
 */
public record StorageRemoveEvent(String name) implements Event {
}
