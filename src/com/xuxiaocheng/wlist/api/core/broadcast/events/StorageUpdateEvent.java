package com.xuxiaocheng.wlist.api.core.broadcast.events;

/**
 * An event that informs that a storage has been renaming/updating.
 * @param id the id of the storage.
 */
public record StorageUpdateEvent(long id) {
}
