package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.impl.ServerStarter;

/**
 * The core server API.
 */
public enum Server {;
    /**
     * Initialize the core server.
     * Note that you **must** call this method before calling all the others.
     * You should ensure these directories exist and have permissions to read/write them.
     * The path should be absolute.
     * @param cache the directory of cache.
     * @param data the directory of data.
     */
    public static void initialize(final String cache, final String data) { throw Main.stub(); }

    /**
     * Start a core server.
     * The server is running in extra threads.
     * Note that you **must** ensure the token is valid and not expired.
     * @param defaultPort the port to listen on. (0 means random.)
     * @param token the web user token.
     * @return the real port the server listening on. (On 'localhost:$port'.)
     * @throws com.xuxiaocheng.wlist.api.core.exceptions.MultiInstanceException if called this method twice.
     */
    public static int start(final int defaultPort, final String token) {
        Server.resetToken(token);
        return ServerStarter.start(defaultPort);
    }

    /**
     * Reset the web user token.
     * This method must be called after calling {@link com.xuxiaocheng.wlist.api.web.Account#refresh(String)}.
     * Note that you **must** ensure the token is valid and not expired.
     * @param newToken the new web user token.
     */
    public static native void resetToken(final String newToken);

    /**
     * Stop the core server.
     */
    public static void stop() {
        ServerStarter.stop();
    }

    /**
     * Reset the admin password of the internal server.
     * @return the password for the admin account.
     */
    public static String resetAdminPassword() { throw Main.stub(); }
}
