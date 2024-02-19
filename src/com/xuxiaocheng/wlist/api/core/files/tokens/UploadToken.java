package com.xuxiaocheng.wlist.api.core.files.tokens;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;

/**
 * The upload token.
 * @param token internal token.
 */
public record UploadToken(String token) implements Serializable, Recyclable {
}
