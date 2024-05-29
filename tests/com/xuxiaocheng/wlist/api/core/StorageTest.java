package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.core.storages.Storage;
import com.xuxiaocheng.wlist.api.core.storages.exceptions.StorageNotFoundException;
import com.xuxiaocheng.wlist.api.core.storages.information.StorageListInformation;
import com.xuxiaocheng.wlist.api.core.storages.options.Filter;
import com.xuxiaocheng.wlist.api.core.storages.options.ListStorageOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@DisplayName("Storage")
public final class StorageTest {
    @BeforeAll
    public static void initialize() {
        MainTest.initialize();
    }
    @AfterAll
    public static void uninitialize() {
        MainTest.uninitialize();
    }

    @Nested
    public class Empty {
        @Test
        public void list() throws ExecutionException, InterruptedException {
            final CoreClient client = Basic.connect();
            final String token = Basic.token(client);

            final ListStorageOptions options = new ListStorageOptions(Filter.All, new LinkedHashMap<>(), 0, 1);
            final StorageListInformation information = Storage.list(client, token, options).get();

            Assertions.assertEquals(0, information.total());
            Assertions.assertEquals(0, information.filtered());
            Assertions.assertEquals(List.of(), information.storages());
        }

        @Test
        public void get() throws ExecutionException, InterruptedException {
            final CoreClient client = Basic.connect();
            final String token = Basic.token(client);

            Basic.assertThrowsExactlyExecution(StorageNotFoundException.class, () -> Storage.get(client, token, 1, false).get());
        }

        @Test
        public void remove() throws ExecutionException, InterruptedException {
            final CoreClient client = Basic.connect();
            final String token = Basic.token(client);

            Basic.assertThrowsExactlyExecution(StorageNotFoundException.class, () -> Storage.remove(client, token, 1).get());
        }

        @Test
        public void rename() throws ExecutionException, InterruptedException {
            final CoreClient client = Basic.connect();
            final String token = Basic.token(client);

            Basic.assertThrowsExactlyExecution(StorageNotFoundException.class, () -> Storage.rename(client, token, 1, "").get());
        }

        @Test
        public void readonly() throws ExecutionException, InterruptedException {
            final CoreClient client = Basic.connect();
            final String token = Basic.token(client);

            Basic.assertThrowsExactlyExecution(StorageNotFoundException.class, () -> Storage.setReadonly(client, token, 1, false).get());
        }
    }
}
