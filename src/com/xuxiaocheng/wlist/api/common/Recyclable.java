package com.xuxiaocheng.wlist.api.common;

import com.xuxiaocheng.wlist.api.Main;

/**
 * This reserved interface for future use.
 */
public interface Recyclable extends AutoCloseable {
    /**
     * The reserved interface method.
     */
    default void recycle() { throw Main.stub(); }

    @Override
    default void close() {
       this.recycle();
    }
}
