package com.xuxiaocheng.wlist.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public final class Tester {
    static {
        System.loadLibrary("wlist");
        Main.initialize("./run/cache", "./run/data");
    }

    @BeforeAll
    public static void initialize() {
    }

    @Test
    public void tester() {
    }
}
