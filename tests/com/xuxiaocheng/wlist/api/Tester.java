package com.xuxiaocheng.wlist.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public final class Tester {
    static {
        System.loadLibrary("wlist");
    }

    @BeforeAll
    public static void initialize() {

        Main.initialize("./run/cache", "./run/data");
    }

    @Test
    public void tester() {
    }
}
