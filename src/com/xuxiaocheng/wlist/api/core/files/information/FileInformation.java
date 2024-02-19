package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.Main;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The information of a file/directory.
 * @param id the file/directory id.
 * @param parentId the parent directory id. (For root directory, it equals to id.)
 * @param name the file/directory name.
 * @param isDirectory whether a directory or a file.
 * @param size the file/directory size. (-1 means unknown.)
 * @param createTime creation time.
 * @param updateTime update time.
 */
public record FileInformation(long id, long parentId, String name, boolean isDirectory, long size,
                              ZonedDateTime createTime, ZonedDateTime updateTime)
        implements Serializable, AutoCloseable {
    @Override
    public void close() { throw Main.stub(); }
}
