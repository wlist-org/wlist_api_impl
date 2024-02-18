package com.xuxiaocheng.wlist.api.core.broadcast.events;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;

/**
 * An event that informs that a file/directory has been trashed.
 * @param location the location of the trashed file/directory.
 */
public record FileTrashEvent(FileLocation location) {
}
