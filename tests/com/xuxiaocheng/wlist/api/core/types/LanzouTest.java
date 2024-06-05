package com.xuxiaocheng.wlist.api.core.types;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.common.exceptions.PasswordMismatchedException;
import com.xuxiaocheng.wlist.api.core.Basic;
import com.xuxiaocheng.wlist.api.core.Client;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.storages.Storage;
import com.xuxiaocheng.wlist.api.core.storages.configs.LanzouConfig;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.concurrent.ExecutionException;

@DisplayName("storage: lanzou")
@ExtendWith(Basic.ClientArguments.class)
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
    public void _placeholder() {
    }

    @Nested
    @DisplayName("add")
    public class Add {
        @ParameterizedTest
        @CsvFileSource(files = "tester/lanzou/login.csv")
        public void correct(final String passport, final String password) throws ExecutionException, InterruptedException {
            final CoreClient client = Basic.connect();
            final String token = Basic.token(client);

            final LanzouConfig config = new LanzouConfig(passport, password, -1);
            final long storage;
            try (final StorageInformation information = Assertions.assertDoesNotThrow(() -> Lanzou.Instance.add(client, token, "lanzou", config).get())) {
                Assertions.assertEquals(Lanzou.Instance, information.type());
                storage = information.id();
            }

            Storage.remove(client, token, storage).get();
            Client.close(client);
        }

        @Test
        public void incorrect(final CoreClient client, final @Basic.CoreToken String token) {
            final LanzouConfig config = new LanzouConfig("12345674567", "123456", -1);
            Basic.assertThrowsExactlyExecution(PasswordMismatchedException.class, () -> Lanzou.Instance.add(client, token, "lanzou-incorrect", config).get());
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
    @DisplayName("config")
    public class Config {
        @ParameterizedTest
        @CsvFileSource(files = "tester/lanzou/login.csv")
        public void correct(final String passport, final String password) throws ExecutionException, InterruptedException {
            final CoreClient client = Basic.connect();
            final String token = Basic.token(client);

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
