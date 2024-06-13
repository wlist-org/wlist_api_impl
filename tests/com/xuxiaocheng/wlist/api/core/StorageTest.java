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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * For {@code rootIdStandard}, {@code rootIdEmpty}:
 * <pre>{@literal
 * root (rootIdStandard)
 *  |-- chunk.txt (4k size, context="@wlist small chunk 32 origin len" * 128, md5="fc6cb96d6681a62e22a2bbd32e5e0519")
 *  |-- large.txt (12m size, context="@wlist large file 32 origin len\n" * 393216, md5="a755aeccea7e39b4b2b0fa76dcd1e54b")
 *  |-- empty (rootIdEmpty)
 *  |-- hello
 *      `-- hello.txt (12 size, context="hello world!", md5="fc3ff98e8c6a0d3087d515c0473f8677")
 *  |-- recursion
 *      `-- inner
 *          `-- recursion.txt (14 size, context="recursion test", md5="a1b160de5f20665f2769a6978c64c6ff")
 *  `-- special
 *      `-- empty.txt (0 size)
 * }</pre>
 */
@DisplayName("Storage")
@ExtendWith(Basic.ClientArguments.class)
public class StorageTest {
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
    @DisplayName("empty")
    public class Empty {
        @Test
        public void list(final CoreClient client, final @Basic.CoreToken String token) throws ExecutionException, InterruptedException {
            final ListStorageOptions options = new ListStorageOptions(Filter.All, new LinkedHashMap<>(), 0, 1);
            final StorageListInformation information = Storage.list(client, token, options).get();

            Assertions.assertEquals(0, information.total());
            Assertions.assertEquals(0, information.filtered());
            Assertions.assertEquals(List.of(), information.storages());
            Client.close(client);
        }

        @Test
        public void get(final CoreClient client, final @Basic.CoreToken String token) {
            Basic.thrown(StorageNotFoundException.class, Storage.get(client, token, 1, false));
            Client.close(client);
        }

        @Test
        public void remove(final CoreClient client, final @Basic.CoreToken String token) {
            Basic.thrown(StorageNotFoundException.class, Storage.remove(client, token, 1));
            Client.close(client);
        }

        @Test
        public void rename(final CoreClient client, final @Basic.CoreToken String token) {
            Basic.thrown(StorageNotFoundException.class, Storage.rename(client, token, 1, ""));
            Client.close(client);
        }

        @Test
        public void readonly(final CoreClient client, final @Basic.CoreToken String token) {
            Basic.thrown(StorageNotFoundException.class, Storage.setReadonly(client, token, 1, false));
            Client.close(client);
        }
    }
}
