package com.xuxiaocheng.wlist.api.core.files.confirmations;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.tokens.DownloadToken;

import java.io.Serializable;

/**
 * The confirmation to download a file.
 * @param size the real total download size. (Associate with the from/to parameters in {@link com.xuxiaocheng.wlist.api.core.files.Download#request(com.xuxiaocheng.wlist.api.core.CoreClient, String, com.xuxiaocheng.wlist.api.core.files.FileLocation, long, long)}.)
 * @param token the download token.
 */
public record DownloadConfirmation(long size, DownloadToken token)
        implements Serializable, Recyclable {
}
