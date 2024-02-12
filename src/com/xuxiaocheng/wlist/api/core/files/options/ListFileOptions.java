package com.xuxiaocheng.wlist.api.core.files.options;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Options when listing files.
 */
public record ListFileOptions(Filter filter, LinkedHashMap<Order, Direction> orders, long offset, int limit) implements Serializable {
}