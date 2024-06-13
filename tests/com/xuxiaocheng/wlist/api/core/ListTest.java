package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.common.Direction;
import com.xuxiaocheng.wlist.api.common.either.Either;
import com.xuxiaocheng.wlist.api.core.files.File;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.options.Filter;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;
import com.xuxiaocheng.wlist.api.core.files.options.Order;
import com.xuxiaocheng.wlist.api.core.storages.Storage;
import com.xuxiaocheng.wlist.api.core.types.LanzouTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.LinkedHashMap;
import java.util.Map;

@DisplayName("files - List")
@ExtendWith(Basic.ClientArguments.class)
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

    public static FileListInformation list(final CoreClient client, final String token, final FileLocation parent, final ListFileOptions options) {
        while (true) {
            final Either<FileListInformation, RefreshConfirmation> either = Basic.get(File.list(client, token, parent, options));
            if (either.isLeft())
                //noinspection OptionalGetWithoutIsPresent
                return either.getLeft().get();
            //noinspection OptionalGetWithoutIsPresent
            RefreshTest.refresh(client, either.getRight().get().token());
        }
    }

    @Test
    public void list(final CoreClient client, final @Basic.CoreToken String token) {
        final ListFileOptions bothNameFirst = new ListFileOptions(Filter.Both, new LinkedHashMap<>(Map.of(Order.Name, Direction.ASCEND)), 0, 10);

        final FileLocation root = new FileLocation(this.storage, this.root, true);
        final FileListInformation list = ListTest.list(client, token, root, bothNameFirst);
        Assertions.assertEquals(6, list.total());
        Assertions.assertEquals(6, list.filtered());
        Assertions.assertEquals(6, list.files().size());

        Client.close(client);
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
