package com.xuxiaocheng.wlist.api.core.files.information;

import java.io.Serializable;
import java.util.List;

/**
 * The information list of a file list.
 * @param total the total number of files in the directory.
 * @param filtered the number of files after filtering. (see {@link com.xuxiaocheng.wlist.api.core.files.options.Filter}).
 * @param files the information list.
 */
public record FileListInformation(long total, long filtered, List<FileInformation> files) implements Serializable {
}
