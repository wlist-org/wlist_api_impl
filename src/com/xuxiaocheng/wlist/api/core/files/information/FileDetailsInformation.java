package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.Main;

import java.io.Serializable;
import java.util.List;

/**
 * The detail information of a file/directory.
 * @param basic the basic information.
 * @param path the full path. (Start with the storage name.)
 * @param optionalMd5 the optional(<b>nullable</b>) md5. (This is a lowercase string with a length of 32.) (For directory, it's always null.)
 */
public record FileDetailsInformation(FileInformation basic, List<String> path, String optionalMd5) implements Serializable, AutoCloseable {
    @Override
    public void close() { throw Main.stub(); }
}
