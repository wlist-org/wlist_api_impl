package com.xuxiaocheng.wlist.api;

import com.xuxiaocheng.wlist.api.core.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

    @Test
    public void tester() {
    }
}
