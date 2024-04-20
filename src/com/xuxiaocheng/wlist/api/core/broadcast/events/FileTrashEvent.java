package com.xuxiaocheng.wlist.api.core.broadcast.events;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;

/**
 * An event that informs that a file/directory has been trashed/restored.
 * @param location the location of the trashed file/directory.
 * @param trash true means trashed, false means restored.
 */
public record FileTrashEvent(FileLocation location, boolean trash) implements Event {
}
