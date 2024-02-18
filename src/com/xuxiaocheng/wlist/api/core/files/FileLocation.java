package com.xuxiaocheng.wlist.api.core.files;

import java.io.Serializable;

/**
 * A record that locates a file/directory.
 * @param storage the id of the storage.
 * @param fileId the file/directory id.
 * @param isDirectory true if the location is a directory.
 */
public record FileLocation(long storage, long fileId, boolean isDirectory) implements Serializable {
}
