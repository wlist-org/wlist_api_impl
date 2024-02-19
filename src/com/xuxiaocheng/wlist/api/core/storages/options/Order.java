package com.xuxiaocheng.wlist.api.core.storages.options;

/**
 * Sort the storage list.
 */
public enum Order {
    /**
     * Sort by the storage id.
     */
    Id,

    /**
     * Sort by the storage name. (sort in GBK encoding)
     */
    Name,

    /**
     * true/false. 'true' is ahead of 'false'.
     */
    Shared,

    /**
     * true/false. 'true' is ahead of 'false'.
     */
    Readonly,

    /**
     * Sort by the storage size. (unknown is ahead of known)
     */
    Size,

    /**
     * Sort by the storage create time.
     */
    CreateTime,

    /**
     * Sort by the storage update time.
     */
    UpdateTime,
}
