package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.Main;

/**
 * The core server API.
 */
public enum Server {;
    /**
     * Start a core server.
     * Won't block the caller thread.
     * @param defaultPort the port to listen on. (0 means random.)
     * @param token the web user token.
     * @return the real port the server listening on. (On 'localhost:$port'.)
     * @throws com.xuxiaocheng.wlist.api.core.exceptions.MultiInstanceException if called this method twice.
     * @throws com.xuxiaocheng.wlist.api.exceptions.TokenExpiredException if the token is expired or invalid.
     */
    public static int start(final int defaultPort, final String token) { throw Main.stub(); }

    /**
     * Reset the web user token.
     * This method must be called after calling {@link com.xuxiaocheng.wlist.api.web.Login#refresh(String)}.
     * @param newToken the new web user token.
     * @throws com.xuxiaocheng.wlist.api.exceptions.TokenExpiredException if the token is expired or invalid.
     */
    public static void resetToken(final String newToken) { throw Main.stub(); }

    /**
     * Stop the core server.
     */
    public static void stop() { throw Main.stub(); }

    /**
     * Reset the admin password of the internal server.
     * @return the password for the admin account.
     */
    public static String resetAdminPassword() { throw Main.stub(); }
}
