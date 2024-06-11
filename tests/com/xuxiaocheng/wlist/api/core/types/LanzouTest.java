package com.xuxiaocheng.wlist.api.core.types;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.core.Basic;
import com.xuxiaocheng.wlist.api.core.Client;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.storages.Storage;
import com.xuxiaocheng.wlist.api.core.storages.configs.LanzouConfig;
import com.xuxiaocheng.wlist.api.core.storages.exceptions.IncorrectStorageAccountException;
import com.xuxiaocheng.wlist.api.core.storages.exceptions.InvalidStorageConfigException;
import com.xuxiaocheng.wlist.api.core.storages.information.StorageInformation;
import com.xuxiaocheng.wlist.api.core.storages.types.Lanzou;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@DisplayName("storage: lanzou")
@ExtendWith(Basic.ClientArguments.class)
public class LanzouTest {
    public static String passport;
    public static String password;
    public static long rootId;
    public static long anotherRootId;

    static {
        try (final Scanner scanner = new Scanner(new File("tester/lanzou/login.csv"))) {
            scanner.useDelimiter("[,\n]");
            LanzouTest.passport = scanner.next();
            LanzouTest.password = scanner.next();
            LanzouTest.rootId = scanner.nextLong();
            LanzouTest.anotherRootId = scanner.nextLong();
            assert !scanner.hasNext() : "Multi passports.";
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @BeforeAll
    public static void initialize() {
        MainTest.initialize();
    }
    @AfterAll
    public static void uninitialize() {
        MainTest.uninitialize();
    }

    @Test
    public void _placeholder() {
    }

    public static long add(final CoreClient client, final String token, final String storage) {
        final LanzouConfig config = new LanzouConfig(LanzouTest.passport, LanzouTest.password, LanzouTest.rootId);
        try (final StorageInformation information = Assertions.assertDoesNotThrow(() -> Lanzou.Instance.add(client, token, storage, config).get())) {
            Assertions.assertEquals(Lanzou.Instance, information.type());
            return information.id();
        }
    }

    @Nested
    @DisplayName("add")
    public class Add {
        @Test
        public void correct(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou");

            Assertions.assertDoesNotThrow(() -> Storage.remove(client, token, storage).get());
            Client.close(client);
        }

        @Test
        public void incorrect(final CoreClient client, final @Basic.CoreToken String token) {
            final LanzouConfig config = new LanzouConfig("12345674567", "123456", -1);
            Basic.assertThrowsExactlyExecution(IncorrectStorageAccountException.class, () -> Lanzou.Instance.add(client, token, "lanzou-incorrect", config).get());
            Client.close(client);
        }

        @Test
        public void incorrectConfig(final CoreClient client, final @Basic.CoreToken String token) {
            final LanzouConfig config = new LanzouConfig("12345674567", "123456", -2);
            final InvalidStorageConfigException exception = Basic.assertThrowsExactlyExecution(InvalidStorageConfigException.class, () -> Lanzou.Instance.add(client, token, "lanzou-incorrect", config).get());
            Assertions.assertNotNull(exception.getMessages().get("rootDirectoryId"));
            Client.close(client);
        }
    }

    @Nested
    @DisplayName("update")
    public class Update {
        @Test
        public void correct(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou");

            final LanzouConfig config = new LanzouConfig(LanzouTest.passport, LanzouTest.password, LanzouTest.anotherRootId);
            Assertions.assertDoesNotThrow(() -> Lanzou.Instance.update(client, token, storage, config).get());

            Assertions.assertDoesNotThrow(() -> Storage.remove(client, token, storage).get());
            Client.close(client);
        }

        @Test
        public void same(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou");

            final LanzouConfig config = new LanzouConfig(LanzouTest.passport, LanzouTest.password, -1);
            Assertions.assertDoesNotThrow(() -> Lanzou.Instance.update(client, token, storage, config).get());

            Assertions.assertDoesNotThrow(() -> Storage.remove(client, token, storage).get());
            Client.close(client);
        }

        @Test
        public void incorrect(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou");

            final LanzouConfig config = new LanzouConfig("12345674567", "123456", -1);
            Basic.assertThrowsExactlyExecution(IncorrectStorageAccountException.class, () -> Lanzou.Instance.update(client, token, storage, config).get());

            Assertions.assertDoesNotThrow(() -> Storage.remove(client, token, storage).get());
            Client.close(client);
        }

        @Test
        public void incorrectConfig(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou");

            final LanzouConfig config = new LanzouConfig(LanzouTest.passport, LanzouTest.password, 10000);
            final InvalidStorageConfigException exception = Basic.assertThrowsExactlyExecution(InvalidStorageConfigException.class, () -> Lanzou.Instance.add(client, token, "lanzou-incorrect", config).get());
            Assertions.assertNotNull(exception.getMessages().get("rootDirectoryId"));

            Assertions.assertDoesNotThrow(() -> Storage.remove(client, token, storage).get());
            Client.close(client);
        }
    }

    @Nested
    @DisplayName("config")
    public class Config {
        @Test
        public void correct(final CoreClient client, final @Basic.CoreToken String token) {
            final LanzouConfig config = new LanzouConfig(passport, password, -1);
            Assertions.assertDoesNotThrow(() -> Lanzou.Instance.checkConfig(client, token, config).get());
            Client.close(client);
        }

        @Test
        public void incorrect(final CoreClient client, final @Basic.CoreToken String token) {
            final LanzouConfig config = new LanzouConfig("12345674567", "123456", -2);
            final InvalidStorageConfigException exception = Basic.assertThrowsExactlyExecution(InvalidStorageConfigException.class, () -> Lanzou.Instance.checkConfig(client, token, config).get());
            Assertions.assertNotNull(exception.getMessages().get("rootDirectoryId"));
            Client.close(client);
        }
    }
}
