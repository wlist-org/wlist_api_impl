package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.common.Direction;
import com.xuxiaocheng.wlist.api.common.either.Either;
import com.xuxiaocheng.wlist.api.core.files.File;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException;
import com.xuxiaocheng.wlist.api.core.files.information.FileDetailsInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.options.Filter;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;
import com.xuxiaocheng.wlist.api.core.files.options.Order;
import com.xuxiaocheng.wlist.api.core.storages.Storage;
import com.xuxiaocheng.wlist.api.core.types.LanzouTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@DisplayName("files - List")
@ExtendWith(Basic.ClientArguments.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public abstract class ListTest {
    protected final long storage;
    protected final long root;

    protected ListTest(final long storage, final long root) {
        super();
        this.storage = storage;
        this.root = root;
        assert this.storage != 0;
    }

    @BeforeAll
    public static void initialize() {
        MainTest.initialize();
    }
    @AfterAll
    public static void uninitialize() {
        MainTest.uninitialize();
    }

    public static FileListInformation list(final CoreClient client, final String token, final FileLocation directory, final ListFileOptions options) {
        while (true) {
            final Either<FileListInformation, RefreshConfirmation> either = Basic.get(File.list(client, token, directory, options));
            if (either.isLeft())
                //noinspection OptionalGetWithoutIsPresent
                return either.getLeft().get();
            //noinspection OptionalGetWithoutIsPresent
            RefreshTest.refresh(client, either.getRight().get().token());
        }
    }

    @Nested
    @DisplayName("normal")
    public class Normal {
        @Test
        public void list(final CoreClient client, final @Basic.CoreToken String token) {
            final ListFileOptions options = new ListFileOptions(Filter.Both, new LinkedHashMap<>(), 0, 10);

            final FileLocation root = new FileLocation(ListTest.this.storage, ListTest.this.root, true);
            final FileListInformation list = ListTest.list(client, token, root, options);
            Assertions.assertEquals(6, list.total());
            Assertions.assertEquals(6, list.filtered());
            Assertions.assertEquals(6, list.files().size());

            Client.close(client);
        }

        @Test
        public void limit(final CoreClient client, final @Basic.CoreToken String token) {
            final ListFileOptions options = new ListFileOptions(Filter.Both, new LinkedHashMap<>(Map.of(Order.Name, Direction.ASCEND)), 0, 4);
            final FileLocation root = new FileLocation(ListTest.this.storage, ListTest.this.root, true);
            final FileListInformation list = ListTest.list(client, token, root, options);
            Assertions.assertEquals(6, list.total());
            Assertions.assertEquals(6, list.filtered());
            Assertions.assertEquals(4, list.files().size());
            Assertions.assertEquals("chunk.txt", list.files().get(0).name());
            Assertions.assertEquals("empty", list.files().get(1).name());
            Assertions.assertEquals("hello", list.files().get(2).name());
            Assertions.assertEquals("large.txt", list.files().get(3).name());
            Client.close(client);
        }

        @Test
        public void offset(final CoreClient client, final @Basic.CoreToken String token) {
            final ListFileOptions options = new ListFileOptions(Filter.Both, new LinkedHashMap<>(Map.of(Order.Name, Direction.ASCEND)), 4, 10);
            final FileLocation root = new FileLocation(ListTest.this.storage, ListTest.this.root, true);
            final FileListInformation list = ListTest.list(client, token, root, options);
            Assertions.assertEquals(6, list.total());
            Assertions.assertEquals(6, list.filtered());
            Assertions.assertEquals(2, list.files().size());
            Assertions.assertEquals("recursion", list.files().get(0).name());
            Assertions.assertEquals("special", list.files().get(1).name());
            Client.close(client);
        }
    }

    @Nested
    @DisplayName("filter")
    public class FilterTest {
        @Test
        public void both(final CoreClient client, final @Basic.CoreToken String token) {
            final ListFileOptions options = new ListFileOptions(Filter.Both, new LinkedHashMap<>(), 0, 10);
            final FileLocation root = new FileLocation(ListTest.this.storage, ListTest.this.root, true);
            final FileListInformation list = ListTest.list(client, token, root, options);
            Assertions.assertEquals(6, list.total());
            Assertions.assertEquals(6, list.filtered());
            Assertions.assertEquals(6, list.files().size());
            Client.close(client);
        }

        @Test
        public void directory(final CoreClient client, final @Basic.CoreToken String token) {
            final ListFileOptions options = new ListFileOptions(Filter.OnlyDirectories, new LinkedHashMap<>(), 0, 10);
            final FileLocation root = new FileLocation(ListTest.this.storage, ListTest.this.root, true);
            final FileListInformation list = ListTest.list(client, token, root, options);
            Assertions.assertEquals(6, list.total());
            Assertions.assertEquals(4, list.filtered());
            Assertions.assertEquals(4, list.files().size());
            Client.close(client);
        }

        @Test
        public void file(final CoreClient client, final @Basic.CoreToken String token) {
            final ListFileOptions options = new ListFileOptions(Filter.OnlyFiles, new LinkedHashMap<>(), 0, 10);
            final FileLocation root = new FileLocation(ListTest.this.storage, ListTest.this.root, true);
            final FileListInformation list = ListTest.list(client, token, root, options);
            Assertions.assertEquals(6, list.total());
            Assertions.assertEquals(2, list.filtered());
            Assertions.assertEquals(2, list.files().size());
            Client.close(client);
        }
    }

    @Nested
    @DisplayName("order")
    public class OrderTest {
        @Test
        @Disabled("the same tested in all")
        public void name(final CoreClient client, final @Basic.CoreToken String token) {
            final ListFileOptions options = new ListFileOptions(Filter.Both, new LinkedHashMap<>(Map.of(Order.Name, Direction.ASCEND)), 0, 10);
            final FileLocation root = new FileLocation(ListTest.this.storage, ListTest.this.root, true);
            final FileListInformation list = ListTest.list(client, token, root, options);
            Assertions.assertEquals(6, list.total());
            Assertions.assertEquals(6, list.filtered());
            Assertions.assertEquals(6, list.files().size());
            Assertions.assertEquals("chunk.txt", list.files().get(0).name());
            Assertions.assertEquals("empty", list.files().get(1).name());
            Assertions.assertEquals("hello", list.files().get(2).name());
            Assertions.assertEquals("large.txt", list.files().get(3).name());
            Assertions.assertEquals("recursion", list.files().get(4).name());
            Assertions.assertEquals("special", list.files().get(5).name());
            Client.close(client);
        }

        @Test
        public void suffix(final CoreClient client, final @Basic.CoreToken String token) {
            final LinkedHashMap<Order, Direction> order = new LinkedHashMap<>();
            order.put(Order.Suffix, Direction.ASCEND);
            order.put(Order.Name, Direction.ASCEND);
            final ListFileOptions options = new ListFileOptions(Filter.Both, order, 0, 10);
            final FileLocation root = new FileLocation(ListTest.this.storage, ListTest.this.root, true);
            final FileListInformation list = ListTest.list(client, token, root, options);
            Assertions.assertEquals(6, list.total());
            Assertions.assertEquals(6, list.filtered());
            Assertions.assertEquals(6, list.files().size());
            Assertions.assertEquals("empty", list.files().get(0).name());
            Assertions.assertEquals("hello", list.files().get(1).name());
            Assertions.assertEquals("recursion", list.files().get(2).name());
            Assertions.assertEquals("special", list.files().get(3).name());
            Assertions.assertEquals("chunk.txt", list.files().get(4).name());
            Assertions.assertEquals("large.txt", list.files().get(5).name());
            Client.close(client);
        }

        @Test
        public void directory(final CoreClient client, final @Basic.CoreToken String token) {
            final LinkedHashMap<Order, Direction> order = new LinkedHashMap<>();
            order.put(Order.Directory, Direction.ASCEND);
            order.put(Order.Name, Direction.ASCEND);
            final ListFileOptions options = new ListFileOptions(Filter.Both, order, 0, 10);
            final FileLocation root = new FileLocation(ListTest.this.storage, ListTest.this.root, true);
            final FileListInformation list = ListTest.list(client, token, root, options);
            Assertions.assertEquals(6, list.total());
            Assertions.assertEquals(6, list.filtered());
            Assertions.assertEquals(6, list.files().size());
            Assertions.assertEquals("empty", list.files().get(0).name());
            Assertions.assertEquals("hello", list.files().get(1).name());
            Assertions.assertEquals("recursion", list.files().get(2).name());
            Assertions.assertEquals("special", list.files().get(3).name());
            Assertions.assertEquals("chunk.txt", list.files().get(4).name());
            Assertions.assertEquals("large.txt", list.files().get(5).name());
            Client.close(client);
        }

        @Test
        public void size(final CoreClient client, final @Basic.CoreToken String token) {
            final LinkedHashMap<Order, Direction> order = new LinkedHashMap<>();
            order.put(Order.Size, Direction.ASCEND);
            order.put(Order.Name, Direction.ASCEND);
            final ListFileOptions options = new ListFileOptions(Filter.Both, order, 0, 10);
            final FileLocation root = new FileLocation(ListTest.this.storage, ListTest.this.root, true);
            final FileListInformation list = ListTest.list(client, token, root, options);
            Assertions.assertEquals(6, list.total());
            Assertions.assertEquals(6, list.filtered());
            Assertions.assertEquals(6, list.files().size());
            Assertions.assertEquals("empty", list.files().get(0).name());
            Assertions.assertEquals("hello", list.files().get(1).name());
            Assertions.assertEquals("recursion", list.files().get(2).name());
            Assertions.assertEquals("special", list.files().get(3).name());
            Assertions.assertEquals("chunk.txt", list.files().get(4).name());
            Assertions.assertEquals("large.txt", list.files().get(5).name());
            Client.close(client);
        }
    }

    public static FileDetailsInformation get(final CoreClient client, final String token, final Long parent, final FileLocation file, final boolean check) {
        while (true) {
            try {
                return File.get(client, token, file, check).get();
            } catch (final InterruptedException exception) {
                Assertions.fail(exception);
            } catch (final ExecutionException exception) {
                if (parent != null && exception.getCause() instanceof final FileNotFoundException e) {
                    Assertions.assertEquals(file, e.getLocation());
                    list(client, token, new FileLocation(file.storage(), parent, true),
                            new ListFileOptions(Filter.Both, new LinkedHashMap<>(), 0, 0));
                    continue;
                }
                Assertions.fail(exception);
            }
        }
    }

    protected static void assertMd5(final String expected, final String actual) {
        Assertions.assertTrue(actual == null || Objects.equals(expected, actual), "expected md5 '" + expected + "' != actual '" + actual + '\'');
    }

    @Nested
    @DisplayName("indexed")
    @org.junit.jupiter.api.Order(Integer.MAX_VALUE)
    public class IndexedNested {
        @Test
        public void all(final CoreClient client, final @Basic.CoreToken String token) {
            final ListFileOptions options = new ListFileOptions(Filter.Both, new LinkedHashMap<>(Map.of(Order.Name, Direction.ASCEND)), 0, 10);
            final FileLocation root = new FileLocation(ListTest.this.storage, ListTest.this.root, true);
            final FileListInformation list = ListTest.list(client, token, root, options);
            Assertions.assertEquals(6, list.total());
            Assertions.assertEquals(6, list.filtered());
            Assertions.assertEquals(6, list.files().size());
            Assertions.assertEquals("chunk.txt", list.files().get(0).name());
            Assertions.assertEquals("empty", list.files().get(1).name());
            Assertions.assertEquals("hello", list.files().get(2).name());
            Assertions.assertEquals("large.txt", list.files().get(3).name());
            Assertions.assertEquals("recursion", list.files().get(4).name());
            Assertions.assertEquals("special", list.files().get(5).name());

            final FileLocation empty = new FileLocation(ListTest.this.storage, list.files().get(1).id(), true);
            final FileListInformation emptyList = ListTest.list(client, token, empty, options);
            Assertions.assertEquals(0, emptyList.total());
            Assertions.assertEquals(0, emptyList.filtered());
            Assertions.assertEquals(0, emptyList.files().size());

            final FileLocation hello = new FileLocation(ListTest.this.storage, list.files().get(2).id(), true);
            final FileListInformation helloList = ListTest.list(client, token, hello, options);
            Assertions.assertEquals(1, helloList.total());
            Assertions.assertEquals(1, helloList.filtered());
            Assertions.assertEquals(1, helloList.files().size());
            Assertions.assertEquals("hello.txt", helloList.files().get(0).name());

            final FileLocation recursion = new FileLocation(ListTest.this.storage, list.files().get(4).id(), true);
            final FileListInformation recursionList = ListTest.list(client, token, recursion, options);
            Assertions.assertEquals(1, recursionList.total());
            Assertions.assertEquals(1, recursionList.filtered());
            Assertions.assertEquals(1, recursionList.files().size());
            Assertions.assertEquals("inner", recursionList.files().get(0).name());

            final FileLocation inner = new FileLocation(ListTest.this.storage, recursionList.files().get(0).id(), true);
            final FileListInformation innerList = ListTest.list(client, token, inner, options);
            Assertions.assertEquals(1, innerList.total());
            Assertions.assertEquals(1, innerList.filtered());
            Assertions.assertEquals(1, innerList.files().size());
            Assertions.assertEquals("recursion.txt", innerList.files().get(0).name());

            Client.close(client);
        }

        @Test
        public void get(final CoreClient client, final @Basic.CoreToken String token) {
            final ListFileOptions options = new ListFileOptions(Filter.Both, new LinkedHashMap<>(Map.of(Order.Name, Direction.ASCEND)), 0, 10);
            final FileListInformation list = ListTest.list(client, token, new FileLocation(ListTest.this.storage, ListTest.this.root, true), options);

            final FileDetailsInformation chunk = ListTest.get(client, token, ListTest.this.root, new FileLocation(ListTest.this.storage, list.files().get(0).id(), false), false);
            Assertions.assertEquals(list.files().get(0), chunk.basic()); // chunk.txt
            Assertions.assertEquals(List.of(), chunk.path());
            ListTest.assertMd5("fc6cb96d6681a62e22a2bbd32e5e0519", chunk.optionalMd5());

            final FileDetailsInformation large = ListTest.get(client, token, ListTest.this.root, new FileLocation(ListTest.this.storage, list.files().get(3).id(), false), false);
            Assertions.assertEquals(list.files().get(3), large.basic()); // large.txt
            Assertions.assertEquals(List.of(), large.path());
            ListTest.assertMd5("a755aeccea7e39b4b2b0fa76dcd1e54b", large.optionalMd5());

            final FileLocation helloLocation = new FileLocation(ListTest.this.storage, list.files().get(2).id(), true);
            final FileDetailsInformation helloDirectory = ListTest.get(client, token, ListTest.this.root, helloLocation, false);
            Assertions.assertEquals(list.files().get(2), helloDirectory.basic()); // hello
            Assertions.assertEquals(List.of(), helloDirectory.path());
            ListTest.assertMd5(null, helloDirectory.optionalMd5());

            final FileListInformation helloList = ListTest.list(client, token, helloLocation, options);

            final FileDetailsInformation hello = ListTest.get(client, token, list.files().get(2).id(), new FileLocation(ListTest.this.storage, helloList.files().get(0).id(), false), false);
            Assertions.assertEquals(helloList.files().get(0), hello.basic()); // hello.txt
            Assertions.assertEquals(List.of("hello"), hello.path());
            ListTest.assertMd5("fc3ff98e8c6a0d3087d515c0473f8677", hello.optionalMd5());
        }
    }

    public static class LanzouListTest extends ListTest {
        protected static long staticStorage;

        public LanzouListTest() {
            super(LanzouListTest.staticStorage, LanzouTest.rootIdStandard);
        }

        @BeforeAll
        public static void construct(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou-list-test", LanzouTest.rootIdStandard);
            assert storage != 0;
            LanzouListTest.staticStorage = storage;
        }

        @AfterAll
        public static void deconstruct(final CoreClient client, final @Basic.CoreToken String token) {
            Basic.get(Storage.remove(client, token, LanzouListTest.staticStorage));
            LanzouListTest.staticStorage = 0;
        }
    }
}
