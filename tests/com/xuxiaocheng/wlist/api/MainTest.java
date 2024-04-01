package com.xuxiaocheng.wlist.api;

import com.xuxiaocheng.wlist.api.core.Client;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class MainTest {
    static {
        System.loadLibrary("wlist");
    }

    @BeforeAll
    public static void initialize() {
        Main.initialize("./run/cache", "./run/data");
    }

    @AfterAll
    public static void uninitialize() {
        Server.stop();
        Main.shutdownThreads();
    }

    private static final AtomicInteger port = new AtomicInteger(0);
    public static CoreClient connect() throws ExecutionException, InterruptedException {
        int port;
        synchronized (MainTest.port) {
            port = MainTest.port.get();
            if (port == 0) {
                port = Server.start(0, "WebToken");
                MainTest.port.set(port);
            }
        }
        return Client.connect("localhost", port).get();
    }

    private static final AtomicReference<String> password = new AtomicReference<>();
    public static String password() {
        String password;
        synchronized (MainTest.password) {
            password = MainTest.password.get();
            if (password == null) {
                password = Server.resetAdminPassword();
                MainTest.password.set(password);
            }
        }
        return password;
    }

    @Test
    public void tester() {
    }
}
