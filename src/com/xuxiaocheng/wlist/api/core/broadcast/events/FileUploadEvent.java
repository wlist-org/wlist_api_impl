package com.xuxiaocheng.wlist.api.core.broadcast.events;

import com.xuxiaocheng.wlist.api.core.files.FileLocation;

/**
 * An event that informs that a file/directory has been uploaded/copied/crated.
 * @param location the location of the new file/directory.
 */
public record FileUploadEvent(FileLocation location) implements Event {
}
