package com.xuxiaocheng.wlist.api;

import com.xuxiaocheng.wlist.api.core.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class MainTest {
    static {
        System.loadLibrary("wlist");
    }

    @BeforeAll
    public static void initialize() {
        Main.initialize("./run/cache", "./run/data");
        try {
            Files.deleteIfExists(Path.of("./run/data", "storages.db"));
            Files.deleteIfExists(Path.of("./run/data", "storages.db-shm"));
            Files.deleteIfExists(Path.of("./run/data", "storages.db-wal"));
            Files.deleteIfExists(Path.of("./run/data", "storages.key"));
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @AfterAll
    public static void uninitialize() {
        Server.stop();
        Main.shutdownThreads();
    }

    @Test
    public void tester() {
    }
}
