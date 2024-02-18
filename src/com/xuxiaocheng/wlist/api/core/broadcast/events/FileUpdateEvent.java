package com.xuxiaocheng.wlist.api.core.broadcast.events;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;

/**
 * An event that informs that a file/directory has been renaming/moving.
 * @param location the location of the file/directory.
 */
public record FileUpdateEvent(FileLocation location) implements Event {
}
