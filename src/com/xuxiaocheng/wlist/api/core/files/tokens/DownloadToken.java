package com.xuxiaocheng.wlist.api.core.files.tokens;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;

/**
 * The download token.
 * @param token internal token.
 */
public record DownloadToken(String token) implements Serializable, Recyclable {
}
