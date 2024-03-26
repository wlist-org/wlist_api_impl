package com.xuxiaocheng.wlist.api.common;

/**
 * This reserved interface for future use.
 */
public interface Recyclable extends AutoCloseable {
    /**
     * The reserved interface method.
     */
    default void recycle() {
        // Do nothing now.
    }

    @Override
    default void close() {
       this.recycle();
    }
}
