package com.xuxiaocheng.wlist.api.core.files.information;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The information of a file/directory.
 * @param id the file/directory id.
 * @param parentId the parent directory id.
 * @param name the file/directory name.
 * @param isDirectory whether a directory or a file.
 * @param size the file/directory size. (-1 means unknown.)
 * @param createTime creation time.
 * @param updateTime update time.
 * @param optionalMd5 the optional(<b>nullable</b>) md5. (This is a lowercase string with a length of 32.)
 */
public record FileInformation(long id, long parentId, String name, boolean isDirectory, long size,
                              ZonedDateTime createTime, ZonedDateTime updateTime,
                              String optionalMd5)
        implements Serializable {
}
