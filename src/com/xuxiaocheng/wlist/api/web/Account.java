package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.common.NetworkFuture;

/**
 * The web account API.
 */
public enum Account {;
    /**
     * Login.
     * You should call {@link com.xuxiaocheng.wlist.api.web.Version#checkWeb()} first to ensure that the API version is correct.
     * @param userId the user's id.
     * @param password the user's password.
     * @return a future, with the token. (expire in 30 minutes.)
     * @see com.xuxiaocheng.wlist.api.web.exceptions.PasswordNotMatchedException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static native NetworkFuture<String> login(final String userId, final String password);

    /**
     * Logout.
     * @param token the token.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static native NetworkFuture<Void> logout(final String token);

    /**
     * Refresh the token. (Then the old token cannot be used.)
     * You may call {@link com.xuxiaocheng.wlist.api.core.Server#resetToken(String)} after calling this method.
     * @param token the token.
     * @return a future, with a new token.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static native NetworkFuture<String> refresh(final String token);
}
