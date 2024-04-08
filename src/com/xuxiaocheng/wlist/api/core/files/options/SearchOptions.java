package com.xuxiaocheng.wlist.api.core.files.options;

/**
 * Options when searching files/directories.
 * @param keyword the keyword to search.
 * @param pattern match pattern mode.
 * @param recursive true means search in recursive directories.
 */
public record SearchOptions(String keyword, Pattern pattern, boolean recursive) {
}
