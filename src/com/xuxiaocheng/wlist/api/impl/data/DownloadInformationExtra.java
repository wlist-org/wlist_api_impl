package com.xuxiaocheng.wlist.api.impl.data;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.information.DownloadInformation;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

public record DownloadInformationExtra(
        FileLocation location,
        DownloadInformation information,
        String optionalUrl,
        String optionalToken,
        byte[] optionalExtra
) implements Serializable, Recyclable {
    public static void serialize(final DownloadInformationExtra self, final MessagePacker packer) throws IOException {
        FileLocation.serialize(self.location, packer);
        DownloadInformation.serialize(self.information, packer);
        if (self.optionalUrl == null) packer.packBoolean(false);
        else packer.packBoolean(true).packString(self.optionalUrl);
        if (self.optionalToken == null) packer.packBoolean(false);
        else packer.packBoolean(true).packString(self.optionalToken);
        if (self.optionalExtra == null) packer.packBoolean(false);
        else packer.packBoolean(true).packBinaryHeader(self.optionalExtra.length).addPayload(self.optionalExtra);
    }

    public static DownloadInformationExtra deserialize(final MessageUnpacker unpacker) throws IOException {
        final FileLocation location = FileLocation.deserialize(unpacker);
        final DownloadInformation information = DownloadInformation.deserialize(unpacker);
        final String url = unpacker.unpackBoolean() ? unpacker.unpackString() : null;
        final String token = unpacker.unpackBoolean() ? unpacker.unpackString() : null;
        final byte[] extra = unpacker.unpackBoolean() ? unpacker.readPayload(unpacker.unpackBinaryHeader()) : null;
        return new DownloadInformationExtra(location, information, url, token, extra);
    }
}
