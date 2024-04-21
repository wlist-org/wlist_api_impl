package com.xuxiaocheng.wlist.api.core.files.options;

/**
 * The pattern when searching.
 */
public enum Pattern {
    /**
     * {@code WHERE name == :keyword}
     */
    FullMatch,

    /**
     * {@code WHERE name REGEXP :keyword}
     */
    Regex,

    /**
     * Using <a href="https://github.com/wangfenjin/simple">an extension of fts5</a>.
     * {@code WHERE name MATCH simple_query(:keyword)}.
     */
    Pinyin,
}
