package com.xuxiaocheng.wlist.api.core.trashes.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;
import java.util.List;

/**
 * The information list of a trashed file list.
 * @param total the total number of files in the directory.
 * @param filtered the number of files after filtering. (see {@link com.xuxiaocheng.wlist.api.core.files.options.Filter}).
 * @param files the information list.
 */
public record TrashListInformation(long total, long filtered, List<TrashInformation> files)
        implements Serializable, Recyclable {
}
