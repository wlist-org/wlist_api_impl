package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.Main;

public enum Server {;
    /**
     * Start a core server.
     * Won't block the caller thread.
     * @param defaultPort the port to listen on (0 means random).
     * @return the real port the server listening on. (On 'localhost:$port'.)
     * @see com.xuxiaocheng.wlist.api.core.exceptions.MultiInstanceException
     * @see com.xuxiaocheng.wlist.api.exceptions.InvalidArgumentException
     * @see com.xuxiaocheng.wlist.api.exceptions.NetworkException
     */
    public static int start(final int defaultPort) { throw Main.stub(); }

    /**
     * Stop the core server.
     */
    public static void stop() { throw Main.stub(); }

    /**
     * Reset the internal core server
     * @return the password for the admin account
     */
    public static String resetAdminPassword() { throw Main.stub(); }
}
