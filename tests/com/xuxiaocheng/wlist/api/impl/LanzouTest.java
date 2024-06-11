package com.xuxiaocheng.wlist.api.impl;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.impl.helper.Lanzou;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.util.Optional;

public class LanzouTest {
    @BeforeAll
    public static void initialize() {
        MainTest.initialize();
    }
    @AfterAll
    public static void uninitialize() {
        MainTest.uninitialize();
    }

    @Test
    @Disabled
    public void _placeholder() {
    }

    @Nested
    @DisplayName("login")
    public class Login {
        @Test
        public void correct() throws IOException {
            final String passport = com.xuxiaocheng.wlist.api.core.types.LanzouTest.passport;
            final String password = com.xuxiaocheng.wlist.api.core.types.LanzouTest.password;
            final Optional<Lanzou.Tokens> tokens = Lanzou.login(passport, password);
            Assertions.assertTrue(tokens.isPresent());
//            System.out.println(tokens);
        }

        @Test
        public void incorrect() throws IOException {
            final Optional<Lanzou.Tokens> tokens = Lanzou.login("12345674567", "123456");
            Assertions.assertFalse(tokens.isPresent());
        }
    }

    @Nested
    @DisplayName("share: file")
    public class ShareFile {
        @ParameterizedTest
        @CsvFileSource(files = "tester/lanzou/share_file.csv")
        public void test(final String url, final String pwd, final int okType) throws IOException {
            final Lanzou.FileUrls urls = Lanzou.getSingleShareFileDownloadUrl(url, pwd);
            switch (okType) {
                case 0 -> { // ok
                    Assertions.assertTrue(urls.available());
                    Assertions.assertTrue(urls.passwordMatched());
                    Assertions.assertNotNull(urls.url());
                }
                case 1 -> // unavailable share file
                    Assertions.assertFalse(urls.available());
                case 2 -> { // password not matched / require password
                    Assertions.assertTrue(urls.available());
                    Assertions.assertFalse(urls.passwordMatched());
                }
                default -> throw new RuntimeException("invalid okType: " + okType);
            }
        }
    }
}
