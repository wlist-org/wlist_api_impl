package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.Tester;
import com.xuxiaocheng.wlist.api.common.NetworkFuture;
import com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException;
import com.xuxiaocheng.wlist.api.web.exceptions.PasswordNotMatchedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.concurrent.ExecutionException;

@DisplayName("Login")
public class LoginTest {
    static {
        Tester.initialize();
    }

    @Nested
    @DisplayName("login")
    public class LoginMethod {
        @Test
        @DisplayName("right password")
        public void loginSuccess() {
            final String token;
            try (final NetworkFuture<String> future = Login.login(1, "change me")) {
                token = Assertions.assertDoesNotThrow(() -> future.get());
            }
            Assertions.assertFalse(token.isBlank());
        }

        @Test
        @DisplayName("wrong password")
        public void loginFailure() {
            try (final NetworkFuture<String> future = Login.login(1, "wrong pw")) {
                final ExecutionException exception = Assertions.assertThrowsExactly(ExecutionException.class, future::get);
                Assertions.assertInstanceOf(PasswordNotMatchedException.class, exception.getCause());
            }
        }

        @Test
        @Disabled
        @DisplayName("hacking")
        public void loginHacking() {
            final ArrayDeque<NetworkFuture<String>> futures = new ArrayDeque<>();
            for (int i = 0; i < 16; ++i)
                futures.add(Login.login(1, "wrong pw"));
            while (!futures.isEmpty())
                try (final NetworkFuture<String> future = futures.remove()) {
                    final ExecutionException exception = Assertions.assertThrowsExactly(ExecutionException.class, future::get);
                    if (exception.getCause() instanceof MatchFrequencyControlException)
                        return;
                }
            Assertions.fail("Should throw MatchFrequencyControlException.");
        }
    }
}
