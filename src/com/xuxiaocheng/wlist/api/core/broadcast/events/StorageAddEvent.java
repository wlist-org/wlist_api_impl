package com.xuxiaocheng.wlist.api.core.broadcast.events;

/**
 * An event that informs that a new storage has been added.
 * @param id the id of the added storage.
 */
public record StorageAddEvent(long id) implements Event {
}
