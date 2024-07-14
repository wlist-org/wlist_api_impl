package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.core.files.Download;
import com.xuxiaocheng.wlist.api.core.files.File;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.Upload;
import com.xuxiaocheng.wlist.api.core.files.confirmations.DownloadConfirmation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.UploadConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileDetailsInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.files.information.UploadChunkInformation;
import com.xuxiaocheng.wlist.api.core.files.information.UploadInformation;
import com.xuxiaocheng.wlist.api.core.files.options.Duplicate;
import com.xuxiaocheng.wlist.api.core.files.tokens.UploadToken;
import com.xuxiaocheng.wlist.api.core.storages.Storage;
import com.xuxiaocheng.wlist.api.core.trashes.Trash;
import com.xuxiaocheng.wlist.api.core.types.LanzouTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@DisplayName("files - Upload")
@ExtendWith(Basic.ClientArguments.class)
public abstract class UploadTest {
    protected final long storage;
    protected final long root;

    protected UploadTest(final long storage, final long root) {
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

    @Test
    public void cancel(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation root = new FileLocation(this.storage, this.root, true);
        final String md5 = Basic.generateRandomString("abcdefghijklmnopqrstuvwxyz", 32);
        final UploadConfirmation confirmation = Basic.get(Upload.request(client, token, root, "CancelTest.txt", 4 << 20 - 1, md5, new String[] { md5 }, Duplicate.Error));
        if (confirmation.done()) {
            final FileInformation information = Basic.get(Upload.finish(client, confirmation.token()));
            final FileLocation location = new FileLocation(this.storage, information.id(), false);
            Basic.get(Trash.trash(client, token, location));
            Basic.get(Trash.delete(client, token, location));
            Assumptions.abort("md5: " + md5 + " information: " + information);
        }
        Basic.get(Upload.cancel(client, confirmation.token()));
    }

    public static void upload(final CoreClient client, final UploadToken token, final ByteBuffer content) {
        final UploadInformation information = Basic.get(Upload.confirm(client, token));
        for (int i = 0, chunksSize = information.chunks().size(); i < chunksSize; ++i) {
            final UploadChunkInformation chunk = information.chunks().get(i);
            final ByteBuffer buf = content.slice((int) chunk.start(), (int) chunk.size());
            final MessageDigest sha256 = Basic.getSha256();
            sha256.update(buf);
            final String hash = Basic.digestSha256(sha256);
            final Optional<String> res = Basic.get(Upload.upload(client, token, i, buf.rewind(), new AtomicBoolean(true)));
            Assertions.assertEquals(Optional.of(hash), res);
        }
    }

    protected ByteBuffer checkAndDeleteUploaded(final CoreClient client, final String token, final FileInformation information, final long parent, final List<String> path, final String filename, final int size, final String md5) {
        Assertions.assertEquals(parent, information.parentId());
        Assertions.assertFalse(information.isDirectory());
        Assertions.assertEquals(filename, information.name());
        Assertions.assertEquals(size, information.size());
        final FileLocation location = new FileLocation(this.storage, information.id(), false);
        final FileDetailsInformation details = Basic.get(File.get(client, token, location, false));
        Assertions.assertEquals(information, details.basic());
        Assertions.assertEquals(path, details.path());
        ListTest.assertMd5(md5, details.optionalMd5());
        if (details.optionalThumbnail() != null)
            Basic.get(Download.cancel(client, details.optionalThumbnail().token()));
        final DownloadConfirmation confirmation = Basic.get(Download.request(client, token, location, 0, Long.MAX_VALUE));
        Assertions.assertEquals(size, confirmation.size());
        final ByteBuffer downloaded = DownloadTest.download(client, confirmation.token(), size);
        Basic.get(Trash.trash(client, token, location));
        Basic.get(Trash.delete(client, token, location));
        return downloaded;
    }

    @Test
    public void same(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation root = new FileLocation(this.storage, this.root, true);
        final String md5 = "fc3ff98e8c6a0d3087d515c0473f8677"; // hello.txt
        final UploadConfirmation confirmation = Basic.get(Upload.request(client, token, root, "UploadSame.txt", 12, md5, new String[] { md5 }, Duplicate.Error));
        if (!confirmation.done()) {
            final ByteBuffer buffer = ByteBuffer.allocateDirect(12).put("hello world!".getBytes(StandardCharsets.UTF_8)).rewind();
            UploadTest.upload(client, confirmation.token(), buffer);
        }
        final FileInformation information = Basic.get(Upload.finish(client, confirmation.token()));
//        System.out.println(information);
        final ByteBuffer buffer = this.checkAndDeleteUploaded(client, token, information, this.root, List.of(), "UploadSame.txt", 12, md5);
        final byte[] bytes = new byte[12];
        buffer.get(bytes);
        Assertions.assertEquals("hello world!", new String(bytes));
    }

    @Test
    public void random(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation root = new FileLocation(this.storage, this.root, true);
        final String content = Basic.generateRandomString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ,.?!'\"", 128);
//        System.out.println("Uploading random content: " + content);
        final MessageDigest digest = Basic.getMd5();
        digest.update(content.getBytes(StandardCharsets.UTF_8));
        final String md5 = Basic.digestMd5(digest);
        final UploadConfirmation confirmation = Basic.get(Upload.request(client, token, root, "UploadRandom.txt", 128, md5, new String[] { md5 }, Duplicate.Error));
        if (confirmation.done()) {
            final FileInformation information = Basic.get(Upload.finish(client, confirmation.token()));
            final FileLocation location = new FileLocation(this.storage, information.id(), false);
            Basic.get(Trash.trash(client, token, location));
            Basic.get(Trash.delete(client, token, location));
            Assumptions.abort("md5: " + md5 + " information: " + information);
        }
        final ByteBuffer buffer = ByteBuffer.allocateDirect(128).put(content.getBytes(StandardCharsets.UTF_8)).rewind();
        UploadTest.upload(client, confirmation.token(), buffer);
        final FileInformation file = Basic.get(Upload.finish(client, confirmation.token()));
//        System.out.println(file);
        final ByteBuffer downloaded = this.checkAndDeleteUploaded(client, token, file, this.root, List.of(), "UploadRandom.txt", 128, md5);
        final byte[] bytes = new byte[128];
        downloaded.get(bytes);
        Assertions.assertEquals(content, new String(bytes));
    }

    @Test
    public void large(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation root = new FileLocation(this.storage, this.root, true);
        final String chunk = Basic.generateRandomString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ,.?!'\"\n", 128);
        final MessageDigest digest = Basic.getMd5();
        for (int i = 0; i < 16 << 13; ++i)
            digest.update(chunk.getBytes(StandardCharsets.UTF_8));
        final String md5 = Basic.digestMd5(digest);
        for (int i = 0; i < 16 << 13 / 4; ++i)
            digest.update(chunk.getBytes(StandardCharsets.UTF_8));
        final String partMd5 = Basic.digestMd5(digest);
        final UploadConfirmation confirmation = Basic.get(Upload.request(client, token, root, "UploadLarge.txt", 16 << 20, md5, new String[] { partMd5, partMd5, partMd5, partMd5 }, Duplicate.Error));
        if (confirmation.done()) {
            final FileInformation information = Basic.get(Upload.finish(client, confirmation.token()));
            final FileLocation location = new FileLocation(this.storage, information.id(), false);
            Basic.get(Trash.trash(client, token, location));
            Basic.get(Trash.delete(client, token, location));
            Assumptions.abort("md5: " + md5 + " information: " + information);
        }
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 << 20).put(chunk.repeat(16 << 13).getBytes(StandardCharsets.UTF_8)).rewind();
        UploadTest.upload(client, confirmation.token(), buffer);
        final FileInformation file = Basic.get(Upload.finish(client, confirmation.token()));
//        System.out.println(file);
        final ByteBuffer downloaded = this.checkAndDeleteUploaded(client, token, file, this.root, List.of(), "UploadLarge.txt", 16 << 20, md5);
        final byte[] bytes = new byte[128];
        downloaded.get(bytes);
        Assertions.assertEquals(chunk, new String(bytes));
    }

    @Test
    @Disabled("require manual check")
    public void pause(final CoreClient client, final @Basic.CoreToken String token) throws InterruptedException {
        final FileLocation root = new FileLocation(this.storage, this.root, true);
        final String md5 = Basic.generateRandomString("abcdefghijklmnopqrstuvwxyz", 32);
        final UploadConfirmation confirmation = Basic.get(Upload.request(client, token, root, "UploadPause.txt", 4 << 20 - 1, md5, new String[] { md5 }, Duplicate.Error));
        if (confirmation.done()) {
            final FileInformation information = Basic.get(Upload.finish(client, confirmation.token()));
            final FileLocation location = new FileLocation(this.storage, information.id(), false);
            Basic.get(Trash.trash(client, token, location));
            Basic.get(Trash.delete(client, token, location));
            Assumptions.abort("md5: " + md5 + " information: " + information);
        }
        final UploadInformation information = Basic.get(Upload.confirm(client, confirmation.token()));
        final UploadChunkInformation chunk = information.chunks().get(0);
        final String content = md5.repeat(4 << 10); // this content is not important.
        final ByteBuffer buffer = ByteBuffer.allocateDirect(4 << 15).put(content.getBytes(StandardCharsets.UTF_8)).rewind();
        final ByteBuffer buf = buffer.slice((int) chunk.start(), Math.min((int) chunk.size(), 4 << 15));
        final AtomicBoolean controller = new AtomicBoolean(true);
        final Thread thread = new Thread(() -> Basic.get(Upload.upload(client, confirmation.token(), 0, buf, controller)));
        thread.start();
        for (int i = 0; i < 30; ++i) {
            System.out.println(buffer.position());
            TimeUnit.MILLISECONDS.sleep(150);
        }
        controller.set(false);
        System.out.println("Paused!");
        for (int i = 0; i < 30; ++i) {
            System.out.println(buffer.position());
            TimeUnit.MILLISECONDS.sleep(150);
        }
        controller.set(true);
        System.out.println("Resumed!");
        for (int i = 0; i < 30; ++i) {
            System.out.println(buffer.position());
            TimeUnit.MILLISECONDS.sleep(150);
        }
        Basic.get(Upload.cancel(client, confirmation.token()));
    }


    public static class LanzouUploadTest extends UploadTest {
        protected static long staticStorage;

        public LanzouUploadTest() {
            super(LanzouUploadTest.staticStorage, LanzouTest.rootIdEmpty);
        }

        @BeforeAll
        public static void construct(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou-upload-test", LanzouTest.rootIdEmpty);
            assert storage != 0;
            LanzouUploadTest.staticStorage = storage;
        }

        @AfterAll
        public static void deconstruct(final CoreClient client, final @Basic.CoreToken String token) {
            Basic.get(Storage.remove(client, token, LanzouUploadTest.staticStorage));
            LanzouUploadTest.staticStorage = 0;
        }
    }
}
