package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.core.files.exceptions.ComplexOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.random.RandomGenerator;

public enum Basic {;
    private static final AtomicInteger port = new AtomicInteger(0);
    public static CoreClient connect() throws ExecutionException, InterruptedException {
        int port;
        synchronized (Basic.port) {
            port = Basic.port.get();
            if (port == 0) {
                port = Server.start(0, "WebToken");
                Basic.port.set(port);
            }
        }
        return Client.connect("localhost", port).get();
    }

    private static final AtomicReference<String> password = new AtomicReference<>();
    public static String password() {
        String password;
        synchronized (Basic.password) {
            password = Basic.password.get();
            if (password == null) {
                password = Server.resetAdminPassword();
                Basic.password.set(password);
            }
        }
        return password;
    }

    private static final AtomicReference<String> token = new AtomicReference<>();
    public static String token(final CoreClient client) throws ExecutionException, InterruptedException {
        String token;
        synchronized (Basic.token) {
            token = Basic.token.get();
            if (token == null) {
                final CoreClient nonnullClient = client == null ? Basic.connect() : client;
                token = Client.login(nonnullClient, "admin", Basic.password()).get();
                if (client == null) nonnullClient.close();
                Basic.token.set(token);
            }
        }
        return token;
    }


    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface CoreToken {
    }

    public static class ClientArguments implements ParameterResolver {
        @Override
        public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
            final int index = parameterContext.getIndex();
            final Class<?> type = parameterContext.getParameter().getType();
            return (index == 0 && type == CoreClient.class) ||
                    (index == 1 && type == String.class && parameterContext.findAnnotation(Basic.CoreToken.class).isPresent());
        }

        @Override
        public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
            final int index = parameterContext.getIndex();
            try {
                if (index == 0)
                    return Basic.connect();
                if (index == 1)
                    return Basic.token(null);
            } catch (final ExecutionException | InterruptedException e) {
                throw new ParameterResolutionException("During connecting/logging", e);
            }
            return null;
        }
    }


    public static <T> T get(final CompletableFuture<T> future) {
        try {
            return future.get();
        } catch (final ExecutionException | InterruptedException exception) {
            if (exception.getCause() instanceof ComplexOperationException)
                return Assumptions.abort();
            return Assertions.fail(exception);
        }
    }

    public static <T extends Throwable> T thrown(final Class<T> expected, final CompletableFuture<?> future) {
        final ExecutionException exception = Assertions.assertThrowsExactly(ExecutionException.class, future::get);
        return Assertions.assertThrowsExactly(expected, () -> { throw exception.getCause(); });
    }


    public static String generateRandomString(final String origin, final int len) {
        return RandomGenerator.getDefault().ints(len, 0, origin.length())
                .boxed().map(origin::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }


    public static MessageDigest getMd5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String digestMd5(final MessageDigest md5) {
        final BigInteger i = new BigInteger(1, md5.digest());
        return String.format("%32s", i.toString(16)).replace(' ', '0');
    }

    public static MessageDigest getSha256() {
        try {
            return MessageDigest.getInstance("Sha256");
        } catch (final NoSuchAlgorithmException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String digestSha256(final MessageDigest sha256) {
        final BigInteger i = new BigInteger(1, sha256.digest());
        return String.format("%64s", i.toString(16)).replace(' ', '0');
    }
}
