package com.xuxiaocheng.wlist.api.core.storages.information;

import com.xuxiaocheng.wlist.api.core.storages.types.StorageType;

/**
 * The information of a storage.
 * @param name the name of the storage.
 * @param readOnly true if the storage is read-only.
 * @param type the type of the storage.
 */
public record StorageInformation(String name, boolean readOnly, StorageType type) {
}
