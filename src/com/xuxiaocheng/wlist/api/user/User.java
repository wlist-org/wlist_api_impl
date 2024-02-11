package com.xuxiaocheng.wlist.api.user;

import com.xuxiaocheng.wlist.api.Main;

import java.util.concurrent.CompletableFuture;

public enum User {;
    /**
     * Reset the user's password.
     * @param token the token.
     * @param old the old password.
     * @param password the new password to set.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.user.exceptions.PasswordNotMatchedException
     */
    public static CompletableFuture<Void> resetPassword(final String token, final String old, final String password) { return Main.future(); }

    /**
     * Get the user's nickname.
     * @param token the token.
     * @return a future, with the user's nickname.
     * @see com.xuxiaocheng.wlist.api.exceptions.TokenExpiredException
     */
    public static CompletableFuture<String> getNickname(final String token) { return Main.future(); }

    /**
     * Set the user's nickname.
     * @param token the token.
     * @param nickname the new nickname.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.exceptions.TooLargeDataException
     */
    public static CompletableFuture<Void> setNickname(final String token, final String nickname) { return Main.future(); }
}
