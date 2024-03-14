package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.common.NetworkFuture;

/**
 * The web user API.
 */
public enum User {;
    /**
     * Reset the user's password.
     * Notice the method will expire all tokens of this user.
     * Then you should call {@link com.xuxiaocheng.wlist.api.web.Account#login(String, String)} to get new token.
     * @param token the token.
     * @param old the old password.
     * @param password the new password to set. (6 <= length <= 128)
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.common.exceptions.IncorrectArgumentException
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.PasswordNotMatchedException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static native NetworkFuture<Void> resetPassword(final String token, final String old, final String password);

    /**
     * Get the user's nickname.
     * @param token the token.
     * @return a future, with the user's nickname.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static native NetworkFuture<String> getNickname(final String token);

    /**
     * Set the user's nickname.
     * @param token the token.
     * @param nickname the new nickname. (1 <= length <= 128)
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.common.exceptions.IncorrectArgumentException
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static native NetworkFuture<Void> setNickname(final String token, final String nickname);
}
