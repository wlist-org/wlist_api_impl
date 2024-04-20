package com.xuxiaocheng.wlist.api.core.storages.options;

import com.xuxiaocheng.wlist.api.common.Direction;
import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Options when listing storages.
 * @param filter the filter to determine which type of storage to list.
 * @param orders the order in which to list the storage. (The front entry has a higher priority.)
 *               (Note that items of the same priority will be listed in random order.)
 * @param offset the offset of the first item to list.
 * @param limit the maximum number of items to list.
 */
public record ListStorageOptions(Filter filter, LinkedHashMap<Order, Direction> orders, long offset, int limit)
        implements Serializable, Recyclable {
}
