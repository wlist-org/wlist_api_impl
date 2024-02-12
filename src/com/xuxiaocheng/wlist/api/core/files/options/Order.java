package com.xuxiaocheng.wlist.api.core.files.options;

/**
 * Sort the file list.
 */
public enum Order {
    /**
     * Sort by the file/directory id.
     */
    Id,

    /**
     * Sort by the file/directory name. (sort in GBK encoding)
     */
    Name,

    /**
     * true/false. true is ahead of false.
     */
    Directory,

    /**
     * Sort by the file/directory size. (unknown is ahead of known)
     */
    Size,

    /**
     * Sort by the file/directory create time.
     */
    CreateTime,

    /**
     * Sort by the file/directory update time.
     */
    UpdateTime,
}
