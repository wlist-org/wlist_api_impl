package com.xuxiaocheng.wlist.api.core.storages.types;

import java.io.Serializable;
import java.util.Set;

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


    /**
     * Return all the suffixes the storage allowed.
     * Note that empty set means all suffixes are valid.
     * This method is only for fast check, some cases may not be covered.
     * @return all the suffixes the storage allowed.
     */
    default Set<String> allowedSuffixes() { return Set.of(); }

    /**
     * Return all the suffixes the storage disallowed.
     * Note that empty set means all suffixes are valid.
     * This method is only for fast check, some cases may not be covered.
     * @return all the suffixes the storage allowed.
     */
    default Set<String> disallowedSuffixes() { return Set.of(); }

    /**
     * Return all the code points the storage disallowed.
     * Note that empty set means all code points are valid.
     * This method is only for fast check, some cases may not be covered.
     * @return all the code points the storage disallowed.
     */
    default Set<Character> disallowedCharacter() { return Set.of('/', '\\', ':'); }

    /**
     * Return the max length of the filename the storage allowed. (-1 means infinity)
     * This method is only for fast check, some cases may not be covered.
     * @return the max length of the filename the storage allowed.
     */
    default long maxFilenameLength() { return -1; }
}
