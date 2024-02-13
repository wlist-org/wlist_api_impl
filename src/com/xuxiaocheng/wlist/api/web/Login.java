package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.Main;

import java.util.concurrent.CompletableFuture;

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
     */
    public static CompletableFuture<Long> register(final String deviceId, final String password) { return Main.future(); }

    /**
     * Login.
     * @param userId the user's id.
     * @param password the user's password.
     * @return a future, with the token. (expire in 30 minutes.)
     * @see com.xuxiaocheng.wlist.api.web.exceptions.PasswordNotMatchedException
     */
    public static CompletableFuture<String> login(final long userId, final String password) { return Main.future(); }

    /**
     * Refresh the token. (Then the old token cannot be used.)
     * @param token the token.
     * @return a future, with a new token.
     * @see com.xuxiaocheng.wlist.api.exceptions.TokenExpiredException
     */
    public static CompletableFuture<String> refresh(final String token) { return Main.future(); }
}
