package com.xuxiaocheng.wlist.api.core.storages.information;

import com.xuxiaocheng.wlist.api.core.storages.types.StorageType;

/**
 * The information of a storage.
 * @param readOnly true if the storage is read-only.
 * @param type the type of the storage.
 */
public record StorageInformation(boolean readOnly, StorageType type) {
}
