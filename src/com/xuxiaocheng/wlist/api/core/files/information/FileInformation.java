package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;
import java.time.Instant;

/**
 * The information of a file/directory.
 * @param id the file/directory id.
 * @param parentId the parent directory id. (For root directory, it equals to id.)
 * @param name the file/directory name.
 * @param isDirectory whether a directory or a file.
 * @param size the file/directory size. (-1 means unknown.)
 * @param createTime the nullable time when created. (null means unknown.)
 * @param updateTime the nullable time when updated. (null means unknown.)
 */
public record FileInformation(long id, long parentId, String name, boolean isDirectory, long size,
                              Instant createTime, Instant updateTime)
        implements Serializable, Recyclable {
}
