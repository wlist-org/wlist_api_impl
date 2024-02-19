package com.xuxiaocheng.wlist.api.core.storages.options;

/**
 * Filter the storage list.
 * <p>*1: If the storage is shared, it always is readonly.</p>
 * <p>*2: If the storage is private, it is writable default.</p>
 * <p>*3: You can set a private storage to readonly.</p>
 * The following is a table.
 * <p>__________Shared____Private
 * <p>Readonly__always'1__maybe'2
 * <p>Writable__never_____maybe'3
 */
public enum Filter {
    /**
     * Readonly, both shared and private. (1, 2)
     */
    Readonly,

    /**
     * Writable, only private. (3)
     */
    Writable,

    /**
     * Shared, only readonly. (1)
     */
    Shared,

    /**
     * Private, both readonly and writable. (2, 3)
     */
    Private,

    /**
     * Only readonly and private. (2)
     */
    ReadonlyPrivate,

    /**
     * Shared and readonly private. (1, 3)
     */
    Owned,

    /**
     * Do not filter. (1, 2, 3)
     */
    All,
}
