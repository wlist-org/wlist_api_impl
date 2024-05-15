package com.xuxiaocheng.wlist.api.core.trashes.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;
import java.time.Instant;

/**
 * The information of a trashed file/directory.
 * @param id the file/directory id.
 * @param name the file/directory name.
 * @param isDirectory whether a directory or a file.
 * @param size the file/directory size. (-1 means unknown.)
 * @param createTime the nullable time when created. (null means unknown.)
 * @param updateTime the nullable time when updated. (null means unknown.)
 * @param trashTime the nullable time when trashed. (null means unknown.)
 */
public record TrashInformation(long id, String name, boolean isDirectory, long size,
                               Instant createTime, Instant updateTime, Instant trashTime)
        implements Serializable, Recyclable {
}
