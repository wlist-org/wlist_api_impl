package com.xuxiaocheng.wlist.api.core.files.tokens;

import java.io.Serializable;

/**
 * The confirmation to refresh a directory.
 * @param id the confirmation id.
 */
public record RefreshToken(String id) implements Serializable {
}
