package com.xuxiaocheng.wlist.api.core.files.confirmations;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.files.tokens.RefreshToken;

import java.io.Serializable;

/**
 * The confirmation to refresh a directory.
 * @param files the count of files in the directory. (-1 means unknown.)
 * @param directories the count of directories in the directory. (-1 means unknown.)
 * @param token the refresh token.
 */
public record RefreshConfirmation(long files, long directories, RefreshToken token)
        implements Serializable, Recyclable {
}
