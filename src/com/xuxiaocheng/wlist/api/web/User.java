package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.NetworkFuture;

/**
 * The web user API.
 */
public enum User {;
    /**
     * Reset the user's password.
     * @param token the token.
     * @param old the old password.
     * @param password the new password to set.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.PasswordNotMatchedException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static NetworkFuture<Void> resetPassword(final String token, final String old, final String password) { return Main.future(); }

    /**
     * Get the user's nickname.
     * @param token the token.
     * @return a future, with the user's nickname.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static NetworkFuture<String> getNickname(final String token) { return Main.future(); }

    /**
     * Set the user's nickname.
     * @param token the token.
     * @param nickname the new nickname.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static NetworkFuture<Void> setNickname(final String token, final String nickname) { return Main.future(); }
}
