package com.xuxiaocheng.wlist.api.core.broadcast.events;

/**
 * An event that informs that the storage is logging in.
 * @param name the name of the storage.
 * @param login true means at the start of login process, false means at the end of the login process.
 */
public record StorageLoginEvent(String name, boolean login) implements Event {
}
