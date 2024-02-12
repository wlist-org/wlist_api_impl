package com.xuxiaocheng.wlist.api.core.files.beans;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The information of a file/directory.
 * @param id the file/directory id.
 * @param parentId the parent directory id.
 * @param name the file/directory name.
 * @param isDirectory whether a directory or a file.
 * @param size the file/directory size. (-1 means unknown)
 * @param createTime the time of creation
 * @param updateTime the time of update
 */
public record FileInformation(long id, long parentId, String name, boolean isDirectory, long size,
                              ZonedDateTime createTime, ZonedDateTime updateTime)
        implements Serializable {
}
