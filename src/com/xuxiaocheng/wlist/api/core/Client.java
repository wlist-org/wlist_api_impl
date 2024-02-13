package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.Main;

import java.util.concurrent.CompletableFuture;

/**
 * The core client API.
 */
public enum Client {;
    /**
     * Connect to the core server ({@link com.xuxiaocheng.wlist.api.core.Server#start(int)}).
     * @param host the host of server.
     * @param port the port of server.
     * @return a future, with a client instance.
     */
    public static CompletableFuture<CoreClient> connect(final String host, final int port) { return Main.future(); }

    /**
     * Close the client.
     * @param client the core client.
     */
    public static void close(final CoreClient client) { throw Main.stub(); }

    /**
     * Login to the core server.
     * @param client the core client.
     * @param username the user's name. (For internal server, default is 'admin'.)
     * @param password the user's password.
     *                 (For internal server, use {@link com.xuxiaocheng.wlist.api.core.Server#resetAdminPassword()} to get the password.)
     * @return a future, with the core token. (expire in 1 hour.)
     */
    public static CompletableFuture<String> login(final CoreClient client, final String username, final String password) { return Main.future(); }

    /**
     * Refresh the token. (But the old token can still be used.)
     * @param token the core token.
     * @return a future, with a new core token.
     */
    public static CompletableFuture<String> refresh(final String token) { return Main.future(); }
}
