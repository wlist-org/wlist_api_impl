package com.xuxiaocheng.wlist.api.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.function.Executable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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


    public static <T extends Throwable> T assertThrowsExactlyExecution(final Class<T> expected, final Executable executable) {
        final ExecutionException exception = Assertions.assertThrowsExactly(ExecutionException.class, executable);
        return Assertions.assertThrowsExactly(expected, () -> { throw exception.getCause(); });
    }
}
