package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.common.Stable;
import com.xuxiaocheng.wlist.api.common.StableModule;

import java.util.concurrent.CompletableFuture;

/**
 * The web account API.
 */
@Stable(since = "1.2.1", module = StableModule.Web)
public enum Account {;
    /**
     * Login.
     * You should call {@link com.xuxiaocheng.wlist.api.web.Version#checkWeb()} first to ensure that the API version is correct.
     * Notice this future may take a long time to be completed, maybe in 10 secs.
     * @param userId the user's id.
     * @param password the user's password.
     * @return a future, with the token. (expire in 30 minutes.)
     * @see com.xuxiaocheng.wlist.api.common.exceptions.PasswordNotMatchedException
     * @see com.xuxiaocheng.wlist.api.common.exceptions.MatchFrequencyControlException
     */
    public static native CompletableFuture<String> login(final String userId, final String password);

    /**
     * Logout.
     * Unless you pass a blank string or match the frequency control, the method will be completed normally.
     * @param token the token.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.common.exceptions.MatchFrequencyControlException
     */
    public static native CompletableFuture<Void> logout(final String token);

    /**
     * Refresh the token. (Then the old token cannot be used.)
     * You **must** call {@link com.xuxiaocheng.wlist.api.core.Server#resetToken(String)} after calling this method.
     * @param token the token.
     * @return a future, with a new token.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.common.exceptions.MatchFrequencyControlException
     */
    public static native CompletableFuture<String> refresh(final String token);
}
