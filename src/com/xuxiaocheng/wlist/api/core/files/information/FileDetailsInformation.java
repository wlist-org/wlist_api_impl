package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.confirmations.DownloadConfirmation;

import java.io.Serializable;
import java.util.List;

/**
 * The detail information of a file/directory.
 * @param basic the basic information.
 * @param path the full path. (Not contain the storage name and the file/directory name.)
 * @param optionalMd5 the optional(<b>nullable</b>) md5. (This is a lowercase string with a length of 32.) (For directory, it's always null.)
 * @param optionalThumbnail the optional(<b>nullable</b>) thumbnail download confirmation.
 */
public record FileDetailsInformation(FileInformation basic, List<String> path, String optionalMd5, DownloadConfirmation optionalThumbnail)
        implements Serializable, Recyclable {
}
