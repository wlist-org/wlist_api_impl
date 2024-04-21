package com.xuxiaocheng.wlist.api.core.trashes.options;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.options.Pattern;

import java.io.Serializable;

/**
 * Options when searching files/directories.
 * @param keyword the keyword to search.
 * @param pattern match pattern mode.
 */
public record SearchOptions(String keyword, Pattern pattern)
        implements Serializable, Recyclable {
}
