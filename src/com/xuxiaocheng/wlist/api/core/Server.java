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
     * @return the real port the server listening on. (On 'localhost:$port'.)
     * @throws com.xuxiaocheng.wlist.api.core.exceptions.MultiInstanceException if called this method twice.
     */
    public static int start(final int defaultPort) { throw Main.stub(); }

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
