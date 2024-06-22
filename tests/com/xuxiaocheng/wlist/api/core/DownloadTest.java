package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.common.Direction;
import com.xuxiaocheng.wlist.api.core.files.Download;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.confirmations.DownloadConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.DownloadChunkInformation;
import com.xuxiaocheng.wlist.api.core.files.information.DownloadInformation;
import com.xuxiaocheng.wlist.api.core.files.information.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.options.Filter;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;
import com.xuxiaocheng.wlist.api.core.files.options.Order;
import com.xuxiaocheng.wlist.api.core.storages.Storage;
import com.xuxiaocheng.wlist.api.core.types.LanzouTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@DisplayName("files - Download")
@ExtendWith(Basic.ClientArguments.class)
public abstract class DownloadTest {
    protected final long storage;
    protected final long root;

    protected DownloadTest(final long storage, final long root) {
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

    private final AtomicReference<FileLocation> chunkLocation = new AtomicReference<>();
    protected FileLocation chunkLocation(final CoreClient client, final String token) {
        FileLocation chunk;
        synchronized (this.chunkLocation) {
            chunk = this.chunkLocation.get();
            if (chunk == null) {
                final ListFileOptions options = new ListFileOptions(Filter.Both, new LinkedHashMap<>(Map.of(Order.Name, Direction.ASCEND)), 0, 1);
                final FileLocation root = new FileLocation(this.storage, this.root, true);
                final FileListInformation list = ListTest.list(client, token, root, options);
                final long id = list.files().get(0).id();
                chunk = new FileLocation(this.storage, id, false);
                this.chunkLocation.set(chunk);
            }
        }
        return chunk;
    }

    @Test
    public void cancel(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation chunk = this.chunkLocation(client, token);
        final DownloadConfirmation confirmation = Basic.get(Download.request(client, token, chunk, 0, Long.MAX_VALUE));
        Basic.get(Download.cancel(client, confirmation.token()));
    }
    
    public static void ensureChunksCovered(final long size, final DownloadInformation information) {
        final Map<Long, Long> map = new TreeMap<>();
        for (final DownloadChunkInformation chunk: information.chunks())
            map.put(chunk.start(), chunk.size());
        long current = 0;
        for (final Map.Entry<Long, Long> entry: map.entrySet()) {
            Assertions.assertTrue(current >= entry.getKey(), "chunks not covered.");
            current = Math.max(current, entry.getKey() + entry.getValue());
        }
        Assertions.assertTrue(current >= size, "chunks not covered.");
    }

    @Test
    public void chunk(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation chunk = this.chunkLocation(client, token);
        final DownloadConfirmation confirmation = Basic.get(Download.request(client, token, chunk, 0, Long.MAX_VALUE));
        Assertions.assertEquals(4 << 10, confirmation.size());
        final DownloadInformation information = Basic.get(Download.confirm(client, confirmation.token()));
        DownloadTest.ensureChunksCovered(4 << 10, information);
        final ByteBuffer buffer = ByteBuffer.allocateDirect(4 << 10);
        for (int i = 0, chunksSize = information.chunks().size(); i < chunksSize; i++) {
            final DownloadChunkInformation info = information.chunks().get(i);
            final ByteBuffer buf = buffer.slice((int) info.start(), (int) info.size());
            Basic.get(Download.download(client, confirmation.token(), i, buf, 0, new AtomicBoolean(true)));
        }
        Basic.get(Download.finish(client, confirmation.token()));
        for (int i = 0; i < 128; ++i) {
            final byte[] bytes = new byte[32];
            buffer.get(bytes);
            final String content = new String(bytes);
            Assertions.assertEquals("@wlist small chunk 32 origin len", content);
        }
    }

    @Test
    public void large(final CoreClient client, final @Basic.CoreToken String token) {
        final ListFileOptions options = new ListFileOptions(Filter.Both, new LinkedHashMap<>(Map.of(Order.Name, Direction.ASCEND)), 0, 4);
        final FileLocation root = new FileLocation(this.storage, this.root, true);
        final FileListInformation list = ListTest.list(client, token, root, options);
        final FileLocation large = new FileLocation(this.storage, list.files().get(3).id(), false);
        final DownloadConfirmation confirmation = Basic.get(Download.request(client, token, large, 0, Long.MAX_VALUE));
        Assertions.assertEquals(12 << 20, confirmation.size());
        final DownloadInformation information = Basic.get(Download.confirm(client, confirmation.token()));
        DownloadTest.ensureChunksCovered(12 << 20, information);
        final ByteBuffer buffer = ByteBuffer.allocateDirect(12 << 20);
        for (int i = 0, chunksSize = information.chunks().size(); i < chunksSize; i++) {
            final DownloadChunkInformation info = information.chunks().get(i);
            final ByteBuffer buf = buffer.slice((int) info.start(), (int) info.size());
            Basic.get(Download.download(client, confirmation.token(), i, buf, 0, new AtomicBoolean(true)));
        }
        Basic.get(Download.finish(client, confirmation.token()));
        for (int i = 0; i < 393216; ++i) {
            final byte[] bytes = new byte[32];
            buffer.get(bytes);
            final String content = new String(bytes);
            Assertions.assertEquals("@wlist large file 32 origin len\n", content);
        }
    }

    @Test
    public void file(final CoreClient client, final @Basic.CoreToken String token, final @TempDir File temp) throws IOException {
        final File chunk = new File(temp, "chunk.txt");
        Assumptions.assumeTrue(chunk.createNewFile());
        final DownloadConfirmation confirmation = Basic.get(Download.request(client, token, this.chunkLocation(client, token), 0, Long.MAX_VALUE));
        Assertions.assertEquals(4 << 10, confirmation.size());
        final DownloadInformation information = Basic.get(Download.confirm(client, confirmation.token()));
        try (final FileChannel channel = FileChannel.open(chunk.toPath(), StandardOpenOption.READ, StandardOpenOption.WRITE);
             final FileLock ignoredLock = channel.lock(0, confirmation.size(), false)) {
            final MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, confirmation.size());
            for (int i = 0, chunksSize = information.chunks().size(); i < chunksSize; i++) {
                final DownloadChunkInformation info = information.chunks().get(i);
                final ByteBuffer buf = buffer.slice((int) info.start(), (int) info.size());
                Basic.get(Download.download(client, confirmation.token(), i, buf, 0, new AtomicBoolean(true)));
            }
            Basic.get(Download.finish(client, confirmation.token()));
            final MessageDigest md5 = Basic.getMd5();
            md5.update(buffer);
            Assertions.assertEquals("fc6cb96d6681a62e22a2bbd32e5e0519", Basic.digestMd5(md5));
        }
    }


    public static class LanzouDownloadTest extends DownloadTest {
        protected static long staticStorage;

        public LanzouDownloadTest() {
            super(LanzouDownloadTest.staticStorage, LanzouTest.rootIdStandard);
        }

        @BeforeAll
        public static void construct(final CoreClient client, final @Basic.CoreToken String token) {
            final long storage = LanzouTest.add(client, token, "lanzou-download-test", LanzouTest.rootIdStandard);
            assert storage != 0;
            LanzouDownloadTest.staticStorage = storage;
        }

        @AfterAll
        public static void deconstruct(final CoreClient client, final @Basic.CoreToken String token) {
            Basic.get(Storage.remove(client, token, LanzouDownloadTest.staticStorage));
            LanzouDownloadTest.staticStorage = 0;
        }
    }
}
