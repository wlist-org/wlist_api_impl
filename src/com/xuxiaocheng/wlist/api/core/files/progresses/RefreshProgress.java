package com.xuxiaocheng.wlist.api.core.files.progresses;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * The progress of refresh.
 * @param loadedFiles the count of the files that have completed loading information.
 * @param loadedDirectories the count of the directories that have completed loading information.
 * @param totalFiles the count of the files that is known in this directory. (This means that this value may increase.)
 * @param totalDirectories the count of the directories that is known in this directory. (This means that this value may increase.)
 */
public record RefreshProgress(long loadedFiles, long loadedDirectories,
                              long totalFiles, long totalDirectories)
        implements Serializable, Recyclable {
    public static void serialize(final RefreshProgress self, final MessagePacker packer) throws IOException {
        packer.packLong(self.loadedFiles);
        packer.packLong(self.loadedDirectories);
        packer.packLong(self.totalFiles);
        packer.packLong(self.totalDirectories);
    }

    public static RefreshProgress deserialize(final MessageUnpacker unpacker) throws IOException {
        final long loadedFiles = unpacker.unpackLong();
        final long loadedDirectories = unpacker.unpackLong();
        final long totalFiles = unpacker.unpackLong();
        final long totalDirectories = unpacker.unpackLong();
        return new RefreshProgress(loadedFiles, loadedDirectories, totalFiles, totalDirectories);
    }
}
