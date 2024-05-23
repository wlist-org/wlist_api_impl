package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.common.Stable;
import com.xuxiaocheng.wlist.api.common.StableModule;
import com.xuxiaocheng.wlist.api.impl.ClientStarter;
import com.xuxiaocheng.wlist.api.impl.enums.Functions;
import org.msgpack.core.MessageUnpacker;

import java.util.concurrent.CompletableFuture;

/**
 * The core client API.
 */
@Stable(since = "1.7.3", module = StableModule.Core)
public enum Client {;
    /**
     * Connect to the core server ({@link com.xuxiaocheng.wlist.api.core.Server#start(int, String)}).
     * @param host the host of server.
     * @param port the port of server.
     * @return a future, with a client instance.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.UnavailableApiVersionException
     */
    public static CompletableFuture<CoreClient> connect(final String host, final int port) {
        return CoreClient.open(host, port);
    }

    /**
     * Check whether this client is available.
     * @param client the core client.
     * @return true if it is available to continue use.
     */
    public static boolean isAvailable(final CoreClient client) {
        return client.isAvailable();
    }
    
    /**
     * Close the client.
     * @param client the core client.
     */
    public static void close(final CoreClient client) { client.close(); }

    /**
     * Login to the core server.
     * @param client the core client.
     * @param username the user's name. (For internal server, default is 'admin'.)
     * @param password the user's password.
     *                 (For internal server, use {@link com.xuxiaocheng.wlist.api.core.Server#resetAdminPassword()} to get the password.)
     * @return a future, with the core token. (expire in 1 hour.)
     * @see com.xuxiaocheng.wlist.api.common.exceptions.PasswordMismatchedException
     */
    public static CompletableFuture<String> login(final CoreClient client, final String username, final String password) {
        return ClientStarter.client(client, Functions.ClientLogin, packer -> packer.packString(username).packString(password), MessageUnpacker::unpackString);
    }

    /**
     * Refresh the token. (But the old token can still be used.)
     * @param token the core token.
     * @return a future, with a new core token.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<String> refresh(final CoreClient client, final String token) {
        return ClientStarter.client(client, Functions.ClientRefresh, packer -> packer.packString(token), MessageUnpacker::unpackString);
    }
}
