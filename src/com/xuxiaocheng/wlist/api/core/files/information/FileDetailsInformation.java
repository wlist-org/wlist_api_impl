package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.confirmations.DownloadConfirmation;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The detail information of a file/directory.
 * @param basic the basic information.
 * @param trashed true if this file/directory is trashed.
 * @param path the full path. (Not contain the storage name and the file/directory name.)
 * @param optionalMd5 the optional(<b>nullable</b>) md5. (This is a lowercase string with a length of 32.) (For directory, it's always null.)
 * @param optionalThumbnail the optional(<b>nullable</b>) thumbnail download confirmation.
 */
public record FileDetailsInformation(FileInformation basic, boolean trashed, List<String> path, String optionalMd5, DownloadConfirmation optionalThumbnail)
        implements Serializable, Recyclable {
    public static void serialize(final FileDetailsInformation self, final MessagePacker packer) throws IOException {
        FileInformation.serialize(self.basic, packer);
        packer.packBoolean(self.trashed);
        packer.packArrayHeader(self.path.size());
        for (final String path: self.path)
            packer.packString(path);
        if (self.optionalMd5 == null) {
            packer.packBoolean(false);
        } else {
            packer.packBoolean(true);
            packer.packString(self.optionalMd5);
        }
        if (self.optionalThumbnail == null) {
            packer.packBoolean(false);
        } else {
            packer.packBoolean(true);
            DownloadConfirmation.serialize(self.optionalThumbnail, packer);
        }
    }

    public static FileDetailsInformation deserialize(final MessageUnpacker unpacker) throws IOException {
        final FileInformation basic = FileInformation.deserialize(unpacker);
        final boolean trashed = unpacker.unpackBoolean();
        final int size = unpacker.unpackArrayHeader();
        final List<String> path = new ArrayList<>(size);
        for (int i = 0; i < size; ++i)
            path.add(unpacker.unpackString());
        final String optionalMd5 = unpacker.unpackBoolean() ? unpacker.unpackString() : null;
        final DownloadConfirmation optionalThumbnail = unpacker.unpackBoolean() ? DownloadConfirmation.deserialize(unpacker) : null;
        return new FileDetailsInformation(basic, trashed, path, optionalMd5, optionalThumbnail);
    }
}
