package com.xuxiaocheng.wlist.api.common;

import com.xuxiaocheng.wlist.api.MainTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MarkTest {
    @BeforeAll
    public static void initialize() {
        MainTest.initialize();
    }

    @Test
    @SuppressWarnings("ConstantAssertArgument")
    public void impl() {
        Assertions.assertTrue(Marks.WITH_IMPL);
    }

    @Test
    public void commonAPI() {
        Assertions.assertEquals("1.5.0", Marks.COMMON_API_VERSION);
        Assertions.assertEquals("0.1.0", Marks.COMMON_IMPL_VERSION);
    }

    @Test
    public void coreAPI() {
        Assertions.assertEquals("1.18.0", Marks.CORE_API_VERSION);
        Assertions.assertEquals("0.1.0", Marks.CORE_IMPL_VERSION);
    }

    @Test
    public void webAPI() {
        Assertions.assertEquals("1.1.0", Marks.WEB_API_VERSION);
        Assertions.assertEquals("0.1.0", Marks.WEB_IMPL_VERSION);
    }
}
