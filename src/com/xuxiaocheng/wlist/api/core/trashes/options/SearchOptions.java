package com.xuxiaocheng.wlist.api.core.trashes.options;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.options.Pattern;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

/**
 * Options when searching files/directories.
 * @param keyword the keyword to search.
 * @param pattern match pattern mode.
 */
public record SearchOptions(String keyword, Pattern pattern)
        implements Serializable, Recyclable {
    public static void serialize(final SearchOptions self, final MessagePacker packer) throws IOException {
        packer.packString(self.keyword);
        packer.packString(self.pattern.name());
    }

    public static SearchOptions deserialize(final MessageUnpacker unpacker) throws IOException {
        final String keyword = unpacker.unpackString();
        final Pattern pattern = Pattern.valueOf(unpacker.unpackString());
        return new SearchOptions(keyword, pattern);
    }
}
