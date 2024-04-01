package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.MainTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

@DisplayName("Users")
public class UsersTest {
    @BeforeAll
    public static void initialize() {
        MainTest.initialize();
    }
    @AfterAll
    public static void uninitialize() {
        MainTest.uninitialize();
    }

    @Test
    @DisplayName("normally")
    public void test() throws ExecutionException, InterruptedException {
        final CoreClient client = MainTest.connect();
        final String password = MainTest.password();

        final String token = Client.login(client, "admin", password).get();
        final String ignoredToken = Client.refresh(client, token).get();

        Client.close(client);
    }
}
