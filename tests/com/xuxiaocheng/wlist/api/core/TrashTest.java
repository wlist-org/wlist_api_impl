package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.common.either.Either;
import com.xuxiaocheng.wlist.api.core.files.File;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.Upload;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.UploadConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.options.Duplicate;
import com.xuxiaocheng.wlist.api.core.files.options.Filter;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;
import com.xuxiaocheng.wlist.api.core.storages.Storage;
import com.xuxiaocheng.wlist.api.core.trashes.Trash;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashInformation;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashListInformation;
import com.xuxiaocheng.wlist.api.core.trashes.options.ListTrashOptions;
import com.xuxiaocheng.wlist.api.core.types.LanzouTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@DisplayName("trash")
@ExtendWith(Basic.ClientArguments.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class TrashTest {
    protected final long storage;
    protected final long root;

    protected TrashTest(final long storage, final long root) {
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

    public static TrashListInformation list(final CoreClient client, final String token, final long storage, final ListTrashOptions options) {
        while (true) {
            final Either<TrashListInformation, RefreshConfirmation> either = Basic.get(Trash.list(client, token, storage, options));
            if (either.isLeft())
                //noinspection OptionalGetWithoutIsPresent
                return either.getLeft().get();
            //noinspection OptionalGetWithoutIsPresent
            RefreshTest.refresh(client, either.getRight().get().token());
        }
    }

    @Test
    @Order(1)
    public void list(final CoreClient client, final @Basic.CoreToken String token) {
        final TrashListInformation list = TrashTest.list(client, token, this.storage, new ListTrashOptions(Filter.Both, new LinkedHashMap<>(), 0, 1));
        Assertions.assertEquals(0, list.total());
        Assertions.assertEquals(0, list.filtered());
        Assertions.assertEquals(List.of(), list.files());
    }

    @Test
    @Order(2)
    public void trashFile(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation root = new FileLocation(this.storage, this.root, true);
        final UploadConfirmation toRestoreConfirmation = Basic.get(Upload.request(client, token, root, "ToRestore.txt", 11, "4480a2125cea632adbacbc7c8fb5e7a0", new String[] { "4480a2125cea632adbacbc7c8fb5e7a0" }, Duplicate.Error));
        if (!toRestoreConfirmation.done())
            UploadTest.upload(client, toRestoreConfirmation.token(), ByteBuffer.allocateDirect(11).put("to restore.".getBytes(StandardCharsets.UTF_8)).rewind());
        final FileInformation toRestore = Basic.get(Upload.finish(client, toRestoreConfirmation.token()));
        final UploadConfirmation toDeleteConfirmation = Basic.get(Upload.request(client, token, root, "ToDelete.txt", 10, "7963075131d6668d45ceb227249f4f8e", new String[] { "7963075131d6668d45ceb227249f4f8e" }, Duplicate.Error));
        if (!toDeleteConfirmation.done())
            UploadTest.upload(client, toDeleteConfirmation.token(), ByteBuffer.allocateDirect(10).put("to delete.".getBytes(StandardCharsets.UTF_8)).rewind());
        final FileInformation toDelete = Basic.get(Upload.finish(client, toDeleteConfirmation.token()));

        final TrashInformation restore = Basic.get(Trash.trash(client, token, new FileLocation(this.storage, toRestore.id(), false)));
        final TrashInformation delete = Basic.get(Trash.trash(client, token, new FileLocation(this.storage, toDelete.id(), false)));
        Assertions.assertEquals(toRestore.id(), restore.id());
        Assertions.assertEquals("ToRestore.txt", restore.name());
        Assertions.assertFalse(restore.isDirectory());
        Assertions.assertTrue(restore.size() == 11 || restore.size() == -1, "restore.size: " + restore.size());
        Assertions.assertEquals(toDelete.id(), delete.id());
        Assertions.assertEquals("ToDelete.txt", delete.name());
        Assertions.assertFalse(delete.isDirectory());
        Assertions.assertTrue(delete.size() == 10 || delete.size() == -1, "delete.size: " + delete.size());
    }

    @Test
    @Order(3)
    public void trashDirectory(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation root = new FileLocation(this.storage, this.root, true);
        final FileInformation directory = Basic.get(File.mkdir(client, token, root, "directory", Duplicate.Error));
        Assertions.assertEquals(this.root, directory.parentId());
        Assertions.assertEquals("directory", directory.name());
        Assertions.assertTrue(directory.isDirectory());
        Assertions.assertTrue(directory.size() == 0 || directory.size() == -1, "directory.size: " + directory.size());
        final TrashInformation information = Basic.get(Trash.trash(client, token, new FileLocation(this.storage, directory.id(), true)));
        Assertions.assertEquals(directory.id(), information.id());
        Assertions.assertEquals("directory", information.name());
        Assertions.assertTrue(information.isDirectory());
        Assertions.assertTrue(information.size() == 0 || information.size() == -1, "information.size: " + information.size());
    }

    @Test
    @Order(4)
    public void restore(final CoreClient client, final @Basic.CoreToken String token) {
        final TrashListInformation list = TrashTest.list(client, token, this.storage, new ListTrashOptions(Filter.Both, new LinkedHashMap<>(), 0, 10));
        final Optional<TrashInformation> trash = list.files().parallelStream().filter(i -> "ToRestore.txt".equals(i.name())).findAny();
        Assumptions.assumeTrue(trash.isPresent());
        final FileLocation location = new FileLocation(this.storage, trash.get().id(), false);
        final FileInformation file = Basic.get(Trash.restore(client, token, location, this.root));
        Assertions.assertEquals(trash.get().id(), file.id());
        Assertions.assertEquals(this.root, file.parentId());
        Assertions.assertEquals("ToRestore.txt", file.name());
        Assertions.assertFalse(file.isDirectory());
        final FileListInformation restored = ListTest.list(client, token, new FileLocation(this.storage, this.root, true), new ListFileOptions(Filter.Both, new LinkedHashMap<>(), 0, 10));
        Assertions.assertTrue(restored.files().parallelStream().anyMatch(file::equals));
        Basic.get(Trash.trash(client, token, location));
    }

    @Test
    @Order(5)
    public void delete(final CoreClient client, final @Basic.CoreToken String token) {
        final TrashListInformation list = TrashTest.list(client, token, this.storage, new ListTrashOptions(Filter.Both, new LinkedHashMap<>(), 0, 10));
        final Optional<TrashInformation> trash = list.files().parallelStream().filter(i -> "ToDelete.txt".equals(i.name())).findAny();
        Assumptions.assumeTrue(trash.isPresent());
        final FileLocation location = new FileLocation(this.storage, trash.get().id(), false);
        Basic.get(Trash.delete(client, token, location));
        Assumptions.assumeTrue(1 == list.files().parallelStream().filter(i -> "ToDelete.txt".equals(i.name())).count());
        final TrashListInformation listed = TrashTest.list(client, token, this.storage, new ListTrashOptions(Filter.Both, new LinkedHashMap<>(), 0, 10));
        final Optional<TrashInformation> trashed = listed.files().parallelStream().filter(i -> "ToDelete.txt".equals(i.name())).findAny();
        Assertions.assertFalse(trashed.isPresent());
    }

    @Test
    @Order(6)
    public void deleteAll(final CoreClient client, final @Basic.CoreToken String token) {
        final TrashListInformation list = TrashTest.list(client, token, this.storage, new ListTrashOptions(Filter.Both, new LinkedHashMap<>(), 0, 10));
        Assumptions.assumeFalse(list.total() == 0);
        Basic.get(Trash.deleteAll(client, token, this.storage));
        final TrashListInformation trashed = TrashTest.list(client, token, this.storage, new ListTrashOptions(Filter.Both, new LinkedHashMap<>(), 0, 1));
        Assertions.assertEquals(0, trashed.total());
    }

    public static class LanzouTrashTest extends TrashTest {
        protected static long staticStorage;

        public LanzouTrashTest() {
            super(LanzouTrashTest.staticStorage, LanzouTest.rootIdEmpty);
        }

        @BeforeAll
        public static void construct(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou-trash-test", LanzouTest.rootIdEmpty);
            assert storage != 0;
            LanzouTrashTest.staticStorage = storage;
        }

        @AfterAll
        public static void deconstruct(final CoreClient client, final @Basic.CoreToken String token) {
            Basic.get(Storage.remove(client, token, LanzouTrashTest.staticStorage));
            LanzouTrashTest.staticStorage = 0;
        }
    }
}
