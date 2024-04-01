package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.MainTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Version")
public class VersionTest {
    static {
        MainTest.initialize();
    }

    @Test
    @DisplayName("core")
    public void core() {
        final Version version = Assertions.assertDoesNotThrow(() -> Version.checkCore().get());
        Assertions.assertEquals(Version.Latest, version);
    }

    @Test
    @DisplayName("web")
    public void web() {
        final Version version = Assertions.assertDoesNotThrow(() -> Version.checkWeb().get());
        Assertions.assertEquals(Version.Latest, version);
    }
}
