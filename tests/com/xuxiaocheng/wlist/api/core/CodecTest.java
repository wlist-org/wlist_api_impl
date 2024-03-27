package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.Tester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

@DisplayName("Codec")
public class CodecTest {
    static {
        Tester.initialize();
    }

    @Test
    @DisplayName("ping")
    public void testPing() throws ExecutionException, InterruptedException {
        final int port = Server.start(0, "WebToken");
        final CoreClient client;
        try (final var future = Client.connect("localhost", port)) {
            client = future.get();
        }
        Client.close(client);
        Server.stop();
    }
}