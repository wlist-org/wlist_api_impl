package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.core.files.File;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.Upload;
import com.xuxiaocheng.wlist.api.core.files.confirmations.UploadConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.files.options.Duplicate;
import com.xuxiaocheng.wlist.api.core.storages.Storage;
import com.xuxiaocheng.wlist.api.core.trashes.Trash;
import com.xuxiaocheng.wlist.api.core.types.LanzouTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@DisplayName("file - move")
@ExtendWith(Basic.ClientArguments.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class MoveTest {
    protected final long storage;
    protected final long root;

    protected MoveTest(final long storage, final long root) {
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

    private static FileLocation directory;
    private static FileLocation file;

    @Test
    @Order(0)
    public void prepare(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation root = new FileLocation(this.storage, this.root, true);
        final long directory = Basic.get(File.mkdir(client, token, root, "directory", Duplicate.Error)).id();
        final UploadConfirmation confirmation = Basic.get(Upload.request(client, token, root, "file.txt", 12, "fc3ff98e8c6a0d3087d515c0473f8677", new String[] { "fc3ff98e8c6a0d3087d515c0473f8677" }, Duplicate.Error));
        if (!confirmation.done())
            UploadTest.upload(client, confirmation.token(), ByteBuffer.allocateDirect(12).put("hello world!".getBytes(StandardCharsets.UTF_8)).rewind());
        final long file = Basic.get(Upload.finish(client, confirmation.token())).id();
        MoveTest.directory = new FileLocation(this.storage, directory, true);
        MoveTest.file = new FileLocation(this.storage, file, false);
    }

    @Test
    @Order(1)
    public void move(final CoreClient client, final @Basic.CoreToken String token) {
        final FileInformation information = Basic.get(File.move(client, token, MoveTest.file, MoveTest.directory, Duplicate.Error));
        Assertions.assertEquals(MoveTest.file.fileId(), information.id());
        Assertions.assertEquals(MoveTest.directory.fileId(), information.parentId());
        Assertions.assertEquals("file.txt", information.name());
        Assertions.assertFalse(information.isDirectory());
        Assertions.assertTrue(information.size() == 12 || information.size() == -1, "information.size: " + information.size());

        final FileInformation file = Basic.get(File.move(client, token, MoveTest.file, new FileLocation(this.storage, this.root, true), Duplicate.Error));
        Assertions.assertEquals(MoveTest.file.fileId(), file.id());
        Assertions.assertEquals(this.root, file.parentId());
        Assertions.assertEquals("file.txt", file.name());
    }

    @Test
    @Order(2)
    public void copy(final CoreClient client, final @Basic.CoreToken String token) {
        final FileInformation information = Basic.get(File.copy(client, token, MoveTest.file, MoveTest.directory, "file.txt", Duplicate.Error));
        Assertions.assertEquals(MoveTest.directory.fileId(), information.parentId());
        Assertions.assertEquals("file.txt", information.name());
        Assertions.assertFalse(information.isDirectory());
        Assertions.assertTrue(information.size() == 12 || information.size() == -1, "information.size: " + information.size());
    }

    @Test
    @Order(3)
    public void rename(final CoreClient client, final @Basic.CoreToken String token) {
        final FileInformation information = Basic.get(File.rename(client, token, MoveTest.file, "file2.txt", Duplicate.Error));
        Assertions.assertEquals(MoveTest.file.fileId(), information.id());
        Assertions.assertEquals(this.root, information.parentId());
        Assertions.assertEquals("file2.txt", information.name());
        Assertions.assertFalse(information.isDirectory());
        Assertions.assertTrue(information.size() == 12 || information.size() == -1, "information.size: " + information.size());
    }

    @Test
    @Order(4)
    public void cleanup(final CoreClient client, final @Basic.CoreToken String token) {
        Basic.get(Trash.trash(client, token, MoveTest.directory));
        Basic.get(Trash.trash(client, token, MoveTest.file));
        Basic.get(Trash.deleteAll(client, token, this.storage));
    }

    public static class LanzouMoveTest extends MoveTest {
        protected static long staticStorage;

        public LanzouMoveTest() {
            super(LanzouMoveTest.staticStorage, LanzouTest.rootIdEmpty);
        }

        @BeforeAll
        public static void construct(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou-move-test", LanzouTest.rootIdEmpty);
            assert storage != 0;
            LanzouMoveTest.staticStorage = storage;
        }

        @AfterAll
        public static void deconstruct(final CoreClient client, final @Basic.CoreToken String token) {
            Basic.get(Storage.remove(client, token, LanzouMoveTest.staticStorage));
            LanzouMoveTest.staticStorage = 0;
        }
    }
}
