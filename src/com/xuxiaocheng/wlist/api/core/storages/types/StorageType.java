package com.xuxiaocheng.wlist.api.core.storages.types;

import java.io.Serializable;

/**
 * An interface that represents a type of storage.
 * Each implementation should be a singleton.
 * (e.g., An enumeration class with only {@code Instance}.)
 */
public sealed interface StorageType extends Serializable permits Lanzou {
    /**
     * Return true means the storage is private. (User's personal account.)
     * @return true if the storage is private.
     */
    boolean isPrivate();

    /**
     * Return true means the storage is shared. (Other's share link.)
     * @return true if the storage is shared.
     */
    default boolean isShared() { return !this.isPrivate(); }

    static StorageType instanceOf(final String name) {
        return switch (name) {
            case "lanzou" -> Lanzou.Instance;
            default -> throw new IllegalArgumentException("Unknown storage type: " + name);
        };
    }

    static String name(final StorageType type) {
        return switch (type.getClass().getCanonicalName()) {
            case "Lanzou" -> "lanzou";
            default -> throw new IllegalStateException("Unexpected value: " + type.getClass().getCanonicalName());
        };
    }
}
