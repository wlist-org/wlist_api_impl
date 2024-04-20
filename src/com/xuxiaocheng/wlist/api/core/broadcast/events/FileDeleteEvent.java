package com.xuxiaocheng.wlist.api.core.broadcast.events;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;

/**
 * An event that informs that a trashed file/directory has been deleted.
 * @param location the location of the trashed file/directory.
 */
public record FileDeleteEvent(FileLocation location) implements Event {
}
