package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.NetworkFuture;

/**
 * The web login API.
 */
public enum Login {;
    /**
     * Register as a visitor.
     * @param deviceId the union device ID.
     * @param password the user's password.
     * @return a future, with the user's id.
     * @see com.xuxiaocheng.wlist.api.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static NetworkFuture<Long> register(final String deviceId, final String password) { return Main.future(); }


    /**
     * Login.
     * @param userId the user's id.
     * @param password the user's password.
     * @return a future, with the token. (expire in 30 minutes.)
     * @see com.xuxiaocheng.wlist.api.exceptions.UnavailableApiVersionException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.PasswordNotMatchedException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static native NetworkFuture<String> login(final long userId, final String password);

    /**
     * Refresh the token. (Then the old token cannot be used.)
     * You may call {@link com.xuxiaocheng.wlist.api.core.Server#resetToken(String)} after calling this method.
     * @param token the token.
     * @return a future, with a new token.
     * @see com.xuxiaocheng.wlist.api.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static NetworkFuture<String> refresh(final String token) { return Main.future(); }
}
