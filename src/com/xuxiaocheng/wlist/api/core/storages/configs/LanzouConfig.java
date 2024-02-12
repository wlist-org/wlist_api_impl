package com.xuxiaocheng.wlist.api.core.storages.configs;

/**
 * The Lanzou type storage config.
 * @param phoneNumber login phone number
 * @param password login password
 * @param rootDirectoryId root directory id (default -1)
 */
public record LanzouConfig(String phoneNumber, String password, long rootDirectoryId) implements Config {
}
