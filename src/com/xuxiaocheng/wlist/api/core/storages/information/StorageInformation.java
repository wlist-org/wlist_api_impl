package com.xuxiaocheng.wlist.api.core.storages.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.storages.types.StorageType;

import java.io.Serializable;
import java.time.Instant;

/**
 * The information of a storage.
 * @param id the id of the storage.
 * @param name the name of the storage.
 * @param readOnly true if the storage is read-only. (Always true if the type is shared.)
 * @param type the type of the storage.
 * @param available true if the storage is available. (The share link may be canceled or the personal account may be deleted.)
 * @param createTime the time when created.
 * @param updateTime the time when updated.
 * @param rootDirectoryId the root directory id of the storage.
 */
public record StorageInformation(long id, String name, boolean readOnly, StorageType type, boolean available,
                                 Instant createTime, Instant updateTime, long rootDirectoryId)
        implements Serializable, Recyclable {
}
