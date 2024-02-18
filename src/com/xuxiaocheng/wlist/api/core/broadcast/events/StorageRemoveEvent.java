package com.xuxiaocheng.wlist.api.core.broadcast.events;

/**
 * An event that informs that a new storage has been removed.
 * @param id the id of the removed storage.
 */
public record StorageRemoveEvent(long id) implements Event {
}
