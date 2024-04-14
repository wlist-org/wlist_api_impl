package com.xuxiaocheng.wlist.api.core.files.options;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * Options when searching files/directories.
 * @param keyword the keyword to search.
 * @param pattern match pattern mode.
 * @param recursive true means search in recursive directories.
 */
public record SearchOptions(String keyword, Pattern pattern, boolean recursive)
        implements Serializable, Recyclable {
    public static void serialize(final SearchOptions self, final MessagePacker packer) throws IOException {
        packer.packString(self.keyword);
        packer.packString(self.pattern.name());
        packer.packBoolean(self.recursive);
    }

    public static SearchOptions deserialize(final MessageUnpacker unpacker) throws IOException {
        final String keyword = unpacker.unpackString();
        final Pattern pattern = Pattern.valueOf(unpacker.unpackString());
        final boolean recursive = unpacker.unpackBoolean();
        return new SearchOptions(keyword, pattern, recursive);
    }
}
