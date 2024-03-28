package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.Tester;
import com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException;
import com.xuxiaocheng.wlist.api.web.exceptions.MatchFrequencyControlException;
import com.xuxiaocheng.wlist.api.web.exceptions.PasswordNotMatchedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@DisplayName("Account")
public class AccountTest {
    static {
        Tester.initialize();
    }

    @Test
    @DisplayName("normally use")
    public void composeNormally() {
        final String token = Assertions.assertDoesNotThrow(() -> Account.login("1", "change me").get());
        final String refreshed = Assertions.assertDoesNotThrow(() -> Account.refresh(token).get());
        Assertions.assertDoesNotThrow(() -> Account.logout(refreshed).get());
    }

    @Nested
    @DisplayName("login")
    public class Login {
        @Test
        @DisplayName("right password")
        public void loginSuccess() {
            final String token = Assertions.assertDoesNotThrow(() -> Account.login("1", "change me").get());
            Assertions.assertFalse(token.isBlank());
        }

        @Test
        @DisplayName("wrong password")
        public void loginFailure() {
            final ExecutionException exception = Assertions.assertThrowsExactly(ExecutionException.class, () -> Account.login("1", "wrong pw").get());
            Assertions.assertInstanceOf(PasswordNotMatchedException.class, exception.getCause());
        }

        @Test
        @Disabled
        @DisplayName("hacking")
        public void loginHacking() {
            final ArrayDeque<CompletableFuture<String>> futures = new ArrayDeque<>();
            for (int i = 0; i < 8; ++i)
                futures.add(Account.login("1", "wrong pw"));
            while (!futures.isEmpty()) {
                final CompletableFuture<String> future = futures.remove();
                final ExecutionException exception = Assertions.assertThrowsExactly(ExecutionException.class, future::get);
                if (exception.getCause() instanceof MatchFrequencyControlException)
                    return;
            }
            Assertions.fail("Should throw MatchFrequencyControlException.");
        }
    }

    @Nested
    @DisplayName("logout")
    public class Logout {
        @Test
        @DisplayName("normal token")
        public void logoutNormally() {
            final String token = Assertions.assertDoesNotThrow(() -> Account.login("1", "change me").get());

            Assertions.assertDoesNotThrow(() -> Account.logout(token).get());
        }

        @Test
        @DisplayName("blank token")
        public void logoutEmpty() {
            final ExecutionException exception = Assertions.assertThrowsExactly(ExecutionException.class, () -> Account.logout("").get());
            Assertions.assertInstanceOf(TokenExpiredException.class, exception.getCause());
        }

        @Test
        @DisplayName("wrong token")
        public void logoutWrong() {
            Assertions.assertDoesNotThrow(() -> Account.logout("wrong_token.just-a_test").get());
        }
    }

    @Nested
    @DisplayName("refresh")
    public class Refresh {
        @Test
        @DisplayName("normal token")
        public void refreshNormally() {
            final String token = Assertions.assertDoesNotThrow(() -> Account.login("1", "change me").get());

            final String refreshed = Assertions.assertDoesNotThrow(() -> Account.refresh(token).get());
            Assertions.assertFalse(refreshed.isBlank());
        }

        @Test
        @DisplayName("blank token")
        public void refreshEmpty() {
            final ExecutionException exception = Assertions.assertThrowsExactly(ExecutionException.class, Account.refresh("")::get);
            Assertions.assertInstanceOf(TokenExpiredException.class, exception.getCause());
        }

        @Test
        @DisplayName("wrong token")
        public void refreshWrong() {
            final ExecutionException exception = Assertions.assertThrowsExactly(ExecutionException.class, Account.refresh("wrong_token.just-a_test")::get);
            Assertions.assertInstanceOf(TokenExpiredException.class, exception.getCause());
        }

        @Test
        @DisplayName("reuse expired")
        public void reuse() throws InterruptedException {
            final String token = Assertions.assertDoesNotThrow(() -> Account.login("1", "change me").get());
            TimeUnit.SECONDS.sleep(6);
            final String refreshed = Assertions.assertDoesNotThrow(() -> Account.refresh(token).get());
            Assertions.assertNotEquals(token, refreshed);

            final ExecutionException exception = Assertions.assertThrowsExactly(ExecutionException.class, Account.refresh(token)::get);
            Assertions.assertInstanceOf(TokenExpiredException.class, exception.getCause());
            Assertions.assertDoesNotThrow(() -> Account.refresh(refreshed).get());
        }
    }
}
