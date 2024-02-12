package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.Main;

import java.util.concurrent.CompletableFuture;

public enum Client {;
    /**
     * Connect to the core server ({@link com.xuxiaocheng.wlist.api.core.Server}).
     * @param host the host of server.
     * @param port the port of server.
     * @return a future, with a client instance.
     */
    public static CompletableFuture<CoreClient> connect(final String host, final int port) { return Main.future(); }

    /**
     * Login to the core server.
     * @param username the user's name (Default is 'admin' in internal server.)
     * @param password the user's password
     * @return a future, with the core token.
     */
    public static CompletableFuture<String> login(final String username, final String password) { return Main.future(); }
}
