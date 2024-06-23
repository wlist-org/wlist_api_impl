package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.common.Stable;
import com.xuxiaocheng.wlist.api.common.StableModule;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.confirmations.DownloadConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.DownloadInformation;
import com.xuxiaocheng.wlist.api.core.files.tokens.DownloadToken;
import com.xuxiaocheng.wlist.api.impl.ClientStarter;
import com.xuxiaocheng.wlist.api.impl.data.DownloadInformationExtra;
import com.xuxiaocheng.wlist.api.impl.data.InternalDownloadInformation;
import com.xuxiaocheng.wlist.api.impl.data.InternalDownloadInformationCallback;
import com.xuxiaocheng.wlist.api.impl.enums.Functions;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The core download API.
 * <p>For a normal example: <pre> {@code
 * final DownloadConfirmation confirmation = Download.request(client, token, location, 0, Long.MAX_VALUE).get();
 * Assumptions.assumeTrue(confirmation.size() <= Integer.MAX_VALUE);
 * final DownloadInformation information = Download.confirm(client, confirmation.token()).get();
 * final ByteBuffer buffer = ByteBuffer.allocateDirect(confirmation.size());
 * for (int i = 0, chunksSize = information.chunks().size(); i < chunksSize; ++i) {
 *   final DownloadChunkInformation info = information.chunks().get(i);
 *   final ByteBuffer buf = buffer.slice((int) info.start(), (int) info.size());
 *   Download.download(client, confirmation.token(), i, buf, 0, new AtomicBoolean(true)).get();
 * }
 * Download.finish(client, confirmation.token()).get();
 * }</pre>
 * <p>For a file example: <pre> {@code
 * Assumptions.assumeTrue(file.createNewFile());
 * final DownloadConfirmation confirmation = Download.request(client, token, location, 0, Long.MAX_VALUE).get();
 * Assumptions.assumeTrue(confirmation.size() <= Integer.MAX_VALUE);
 * final DownloadInformation information = Download.confirm(client, confirmation.token()).get();
 * try (final FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.READ, StandardOpenOption.WRITE);
 *      final FileLock ignoredLock = channel.lock(0, confirmation.size(), false)) {
 *   final MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, confirmation.size());
 *   for (int i = 0, chunksSize = information.chunks().size(); i < chunksSize; ++i) {
 *     final DownloadChunkInformation info = information.chunks().get(i);
 *     final ByteBuffer buf = buffer.slice((int) info.start(), (int) info.size());
 *     Download.download(client, confirmation.token(), i, buf, 0, new AtomicBoolean(true)).get();
 *   }
 *   Download.finish(client, confirmation.token()).get();
 * }
 * }</pre>
 */
@Stable(since = "1.18.1", module = StableModule.Core)
public enum Download {;
    /**
     * Request to download the file.
     * Note that the download token will lock the file until it is canceled/finished.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file. ({@code assert !file.isDirectory;})
     * @param from the start byte index of the entire file. (include) (0 <= from <= to)
     * @param to the last byte index of the entire file. (include) (For entire file, you can pass {@link java.lang.Long#MAX_VALUE}.)
     * @return a future, with the download confirmation.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.FlowNotEnoughException
     * @see com.xuxiaocheng.wlist.api.common.exceptions.IncorrectArgumentException
     */
    public static CompletableFuture<DownloadConfirmation> request(final CoreClient client, final String token, final FileLocation file, final long from, final long to) {
        return ClientStarter.client(client, Functions.DownloadRequest, packer -> {
            packer.packString(token);
            FileLocation.serialize(file, packer);
            packer.packLong(from);
            packer.packLong(to);
        }, DownloadConfirmation::deserialize);
    }

    /**
     * Cancel a download.
     * @param client the core client.
     * @param token the download token.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<Void> cancel(final CoreClient client, final DownloadToken token) {
        return ClientStarter.client(client, Functions.DownloadCancel, packer -> DownloadToken.serialize(token, packer), ClientStarter::deserializeVoid)
                .thenCompose(ignored -> com.xuxiaocheng.wlist.api.impl.functions.Download.cancelClient(token));
    }

    /**
     * Confirm a download.
     * Note only after being confirmed, the download token will be valid (not expired) (except cancel).
     * @param client the core client.
     * @param token the download token.
     * @return a future, with the download information.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<DownloadInformation> confirm(final CoreClient client, final DownloadToken token) {
        return ClientStarter.client(client, Functions.DownloadConfirm, packer -> DownloadToken.serialize(token, packer), DownloadInformationExtra::deserialize)
                .thenCompose(extra -> com.xuxiaocheng.wlist.api.impl.functions.Download.confirmClient(token, extra).thenApply(ignored -> extra.information()));
    }

    /**
     * Download the file chunk.
     * Note that the buffer needn't be large enough to contain the entire chunk.
     * This method will start downloading from the {@code start} byte
     * and try to fill the {@code buffer} as much as possible.
     * (Call {@code buffer.remaining()} as length)
     * The behavior of this method is similar to sending a GET request with a range header,
     * so don't call this method at high frequency to avoid HTTP 429,
     * <p>You can call {@code buffer.position()} to get the downloading progress.
     * (Not real-time, but at a small interval. Mainly based on HTTP chunk/frame.)
     * <p>You can set the controller to pause or resume the upload.
     * (false means pause, true means resume.)
     * (Internally, use polling every 300ms.)
     * @param client the core client.
     * @param token the download token.
     * @param id the download chunk id. (id >= 0)
     * @param buffer the directly buffer to write the data. ({@code assert buffer.isDirect();})
     * @param start the start position to download of the <b>chunk</b>. (start >= 0)
     * @param controller the download controller.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<Void> download(final CoreClient client, final DownloadToken token, final int id, final ByteBuffer buffer, final long start, final AtomicBoolean controller) {
        return CompletableFuture.completedFuture(null)
                .thenCompose(ignored -> ClientStarter.client(client, Functions.DownloadDownload, packer -> {
                    DownloadToken.serialize(token, packer);
                    packer.packInt(id);
                    packer.packLong(start);
                    packer.packLong(buffer.remaining());
                }, InternalDownloadInformation::deserialize))
                .thenCompose(information -> com.xuxiaocheng.wlist.api.impl.functions.Download.downloadClient(token, information, buffer, controller))
                .thenCompose(callback -> ClientStarter.client(client, Functions.DownloadDownloadCallback, packer -> {
                    DownloadToken.serialize(token, packer);
                    packer.packInt(id);
                    InternalDownloadInformationCallback.serialize(callback, packer);
                }, ClientStarter::deserializeVoid));
    }

    /**
     * Finish a download.
     * This method is similar to call {@link #cancel},
     * but different in log and metric.
     * @param client the core client.
     * @param token the download token.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<Void> finish(final CoreClient client, final DownloadToken token) {
        return ClientStarter.client(client, Functions.DownloadFinish, packer -> DownloadToken.serialize(token, packer), ClientStarter::deserializeVoid)
                .thenCompose(ignored -> com.xuxiaocheng.wlist.api.impl.functions.Download.cancelClient(token));
    }
}
