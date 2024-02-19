package com.xuxiaocheng.wlist.api.core.files.progresses;

import com.xuxiaocheng.wlist.api.Main;

import java.io.Serializable;

/**
 * The progress of refresh.
 * @param loadedFiles the count of the files that have completed loading information.
 * @param loadedDirectories the count of the directories that have completed loading information.
 * @param totalFiles the count of the files that is known in this directory. (This means that this value may increase.)
 * @param totalDirectories the count of the directories that is known in this directory. (This means that this value may increase.)
 */
public record RefreshProgress(long loadedFiles, long loadedDirectories,
                              long totalFiles, long totalDirectories) implements Serializable, AutoCloseable {
    @Override
    public void close() { throw Main.stub(); }
}
