package com.xuxiaocheng.wlist.api.core.broadcast.events;

/**
 * An event that informs that a new storage has been added.
 * @param name the name of the added storage.
 */
public record StorageAddEvent(String name) implements Event {
}
