package com.xuxiaocheng.wlist.api.core.broadcast.events;

/**
 * An event that informs that the storage is logging in.
 * @param id the id of the storage.
 * @param start true means at the start of login process, false means at the end of the login process.
 */
public record StorageLoginEvent(long id, boolean start) implements Event {
}
