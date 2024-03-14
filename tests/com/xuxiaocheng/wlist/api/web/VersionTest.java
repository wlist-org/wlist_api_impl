package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.Tester;
import com.xuxiaocheng.wlist.api.common.NetworkFuture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Version")
public class VersionTest {
    static {
        Tester.initialize();
    }

    @Test
    @DisplayName("core")
    public void core() {
        final Version version;
        try (final NetworkFuture<Version> future = Version.checkCore()) {
            version = Assertions.assertDoesNotThrow(() -> future.get());
        }
        Assertions.assertEquals(Version.Latest, version);
    }

    @Test
    @DisplayName("web")
    public void web() {
        final Version version;
        try (final NetworkFuture<Version> future = Version.checkWeb()) {
            version = Assertions.assertDoesNotThrow(() -> future.get());
        }
        Assertions.assertEquals(Version.Latest, version);
    }
}
