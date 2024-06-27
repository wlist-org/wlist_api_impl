package com.xuxiaocheng.wlist.api.impl.data;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.information.UploadInformation;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;

public record UploadInformationExtra(
        FileLocation parent,
        UploadInformation information,
        String optionalUrl,
        String optionalToken,
        byte[] optionalExtra
) implements Serializable, Recyclable {
    public static void serialize(final UploadInformationExtra self, final MessagePacker packer) throws IOException {
        FileLocation.serialize(self.parent, packer);
        UploadInformation.serialize(self.information, packer);
        if (self.optionalUrl == null) packer.packBoolean(false);
        else packer.packBoolean(true).packString(self.optionalUrl);
        if (self.optionalToken == null) packer.packBoolean(false);
        else packer.packBoolean(true).packString(self.optionalToken);
        if (self.optionalExtra == null) packer.packBoolean(false);
        else packer.packBoolean(true).packBinaryHeader(self.optionalExtra.length).addPayload(self.optionalExtra);
    }

    public static UploadInformationExtra deserialize(final MessageUnpacker unpacker) throws IOException {
        final FileLocation parent = FileLocation.deserialize(unpacker);
        final UploadInformation information = UploadInformation.deserialize(unpacker);
        final String url = unpacker.unpackBoolean() ? unpacker.unpackString() : null;
        final String token = unpacker.unpackBoolean() ? unpacker.unpackString() : null;
        final byte[] extra = unpacker.unpackBoolean() ? unpacker.readPayload(unpacker.unpackBinaryHeader()) : null;
        return new UploadInformationExtra(parent, information, url, token, extra);
    }
}
