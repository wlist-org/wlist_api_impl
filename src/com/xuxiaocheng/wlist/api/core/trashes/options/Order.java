package com.xuxiaocheng.wlist.api.core.trashes.options;

/**
 * Sort the trashed file list.
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
     * Sort by the suffix of name.
     * Split by '.', it is unknown if the name does not contain '.'.
     * (unknown is ahead of known, sort in GBK encoding)
     */
    Suffix,

    /**
     * true/false. 'true' is ahead of 'false'.
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

    /**
     * Sort by the file/directory trash time.
     */
    TrashTime,
}
