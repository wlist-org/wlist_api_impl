package com.xuxiaocheng.wlist.api.core.files.options;

/**
 * Filter the file list.
 */
public enum Filter {
    /**
     * Only directories, no files.
     */
    OnlyDirectories,

    /**
     * Only files, no directories.
     */
    OnlyFiles,

    /**
     * Do not filter.
     */
    Both,
}
