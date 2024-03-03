package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.NetworkFuture;

public enum Register {;
    /**
     * Register as a guest.
     * @param deviceId the union device ID.
     * @param password the user's password.
     * @return a future, with the user's id.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TooLargeDataException
     * @see com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException
     */
    public static NetworkFuture<Long> register(final String deviceId, final String password) { return Main.future(); }

}
