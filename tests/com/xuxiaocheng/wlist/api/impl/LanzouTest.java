package com.xuxiaocheng.wlist.api.impl;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.impl.helper.Lanzou;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;

public class LanzouTest {
    @BeforeAll
    public static void initialize() {
        MainTest.initialize();
    }
    @AfterAll
    public static void uninitialize() {
        MainTest.uninitialize();
    }

    @ParameterizedTest
    @CsvFileSource(files = "tester/lanzou.csv")
    @DisplayName("correct")
    public void test(final String passport, final String password) throws IOException {
        final Lanzou.Tokens tokens = Lanzou.login(passport, password);
        Assertions.assertNotNull(tokens);
//        System.out.println(tokens);
    }

    @Test
    @DisplayName("incorrect")
    public void incorrect() throws IOException {
        Assertions.assertNull(Lanzou.login("12345674567", "123456"));
    }
}
