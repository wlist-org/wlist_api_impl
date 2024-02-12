package com.xuxiaocheng.wlist.api.core.files;

import java.io.Serializable;

/**
 * A record that locates a file/directory.
 * @param storage the storage
 * @param fileId the file/directory id
 * @param isDirectory true if the location is a directory
 */
public record FileLocation(String storage, long fileId, boolean isDirectory) implements Serializable {
}
