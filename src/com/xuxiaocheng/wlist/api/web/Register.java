package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.NetworkFuture;

/**
 * The web register API.
 */
public enum Register {;
    /**
     * Unregister.
     * @param token the token.
     * @param password the login password.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.PasswordNotMatchedException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static NetworkFuture<Void> unregister(final String token, final String password) { return Main.future(); }


    /**
     * Register as a guest.
     * Notice each call of this method will create a new guest account.
     * @param deviceId the union device ID. (16 <= length <= 256)
     * @param password the user's password. (6 <= length <= 128)
     * @return a future, with the user's id.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.IncorrectArgumentException
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static NetworkFuture<String> registerAsGuest(final String deviceId, final String password) { return Main.future(); }

}
