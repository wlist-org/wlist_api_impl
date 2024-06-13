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
import org.junit.jupiter.api.Disabled;
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
    public static long rootIdStandard;
    public static long rootIdEmpty;

    static {
        try (final Scanner scanner = new Scanner(new File("tester/lanzou/account.csv"))) {
            scanner.useDelimiter("[,\n]");
            LanzouTest.passport = scanner.next();
            LanzouTest.password = scanner.next();
            LanzouTest.rootIdStandard = scanner.nextLong();
            LanzouTest.rootIdEmpty = scanner.nextLong();
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
    @Disabled
    public void _placeholder() {
    }

    public static long add(final CoreClient client, final String token, final String storage, final long root) {
        final LanzouConfig config = new LanzouConfig(LanzouTest.passport, LanzouTest.password, root);
        try (final StorageInformation information = Basic.get(Lanzou.Instance.add(client, token, storage, config))) {
            Assertions.assertEquals(Lanzou.Instance, information.type());
            return information.id();
        }
    }

    @Nested
    @DisplayName("add")
    public class Add {
        @Test
        public void correct(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou", LanzouTest.rootIdStandard);

            Assertions.assertDoesNotThrow(() -> Storage.remove(client, token, storage).get());
            Client.close(client);
        }

        @Test
        public void incorrect(final CoreClient client, final @Basic.CoreToken String token) {
            final LanzouConfig config = new LanzouConfig("12345674567", "123456", -1);
            Basic.thrown(IncorrectStorageAccountException.class, Lanzou.Instance.add(client, token, "lanzou-incorrect", config));
            Client.close(client);
        }

        @Test
        public void incorrectConfig(final CoreClient client, final @Basic.CoreToken String token) {
            final LanzouConfig config = new LanzouConfig("12345674567", "123456", -2);
            final InvalidStorageConfigException exception = Basic.thrown(InvalidStorageConfigException.class, Lanzou.Instance.add(client, token, "lanzou-incorrectConfig", config));
            Assertions.assertNotNull(exception.getMessages().get("rootDirectoryId"));
            Client.close(client);
        }
    }

    @Nested
    @DisplayName("update")
    public class Update {
        @Test
        public void correct(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou", LanzouTest.rootIdStandard);

            final LanzouConfig config = new LanzouConfig(LanzouTest.passport, LanzouTest.password, LanzouTest.rootIdEmpty);
            Assertions.assertDoesNotThrow(() -> Lanzou.Instance.update(client, token, storage, config).get());

            Assertions.assertDoesNotThrow(() -> Storage.remove(client, token, storage).get());
            Client.close(client);
        }

        @Test
        public void same(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou", LanzouTest.rootIdStandard);

            final LanzouConfig config = new LanzouConfig(LanzouTest.passport, LanzouTest.password, LanzouTest.rootIdStandard);
            Assertions.assertDoesNotThrow(() -> Lanzou.Instance.update(client, token, storage, config).get());

            Assertions.assertDoesNotThrow(() -> Storage.remove(client, token, storage).get());
            Client.close(client);
        }

        @Test
        public void incorrect(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou", LanzouTest.rootIdStandard);

            final LanzouConfig config = new LanzouConfig("12345674567", "123456", -1);
            Basic.thrown(IncorrectStorageAccountException.class, Lanzou.Instance.update(client, token, storage, config));

            Assertions.assertDoesNotThrow(() -> Storage.remove(client, token, storage).get());
            Client.close(client);
        }

        @Test
        public void incorrectConfig(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou", LanzouTest.rootIdStandard);

            final LanzouConfig config = new LanzouConfig(LanzouTest.passport, LanzouTest.password, 10000);
            final InvalidStorageConfigException exception = Basic.thrown(InvalidStorageConfigException.class, Lanzou.Instance.update(client, token, storage, config));
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
            final LanzouConfig config = new LanzouConfig(LanzouTest.passport, LanzouTest.password, LanzouTest.rootIdStandard);
            Assertions.assertDoesNotThrow(() -> Lanzou.Instance.checkConfig(client, token, config).get());
            Client.close(client);
        }

        @Test
        public void incorrect(final CoreClient client, final @Basic.CoreToken String token) {
            final LanzouConfig config = new LanzouConfig("12345674567", "123456", -2);
            final InvalidStorageConfigException exception = Basic.thrown(InvalidStorageConfigException.class, Lanzou.Instance.checkConfig(client, token, config));
            Assertions.assertNotNull(exception.getMessages().get("rootDirectoryId"));
            Client.close(client);
        }
    }
}
