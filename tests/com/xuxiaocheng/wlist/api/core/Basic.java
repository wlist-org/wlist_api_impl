package com.xuxiaocheng.wlist.api.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public enum Basic {;
    private static final AtomicInteger port = new AtomicInteger(0);
    public static CoreClient connect() throws ExecutionException, InterruptedException {
        int port;
        synchronized (Basic.port) {
            port = Basic.port.get();
            if (port == 0) {
                port = Server.start(0, "WebToken");
                Basic.port.set(port);
            }
        }
        return Client.connect("localhost", port).get();
    }

    private static final AtomicReference<String> password = new AtomicReference<>();
    public static String password() {
        String password;
        synchronized (Basic.password) {
            password = Basic.password.get();
            if (password == null) {
                password = Server.resetAdminPassword();
                Basic.password.set(password);
            }
        }
        return password;
    }

    private static final AtomicReference<String> token = new AtomicReference<>();
    public static String token(final CoreClient client) throws ExecutionException, InterruptedException {
        String token;
        synchronized (Basic.token) {
            token = Basic.token.get();
            if (token == null) {
                token = Client.login(client, "admin", Basic.password()).get();
                Basic.token.set(token);
            }
        }
        return token;
    }

    public static <T extends Throwable> T assertThrowsExactlyExecution(final Class<T> expected, final Executable executable) {
        final ExecutionException exception = Assertions.assertThrowsExactly(ExecutionException.class, executable);
        return Assertions.assertThrowsExactly(expected, () -> { throw exception.getCause(); });
    }
}
