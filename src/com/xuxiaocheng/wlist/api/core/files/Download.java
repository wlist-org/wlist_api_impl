package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.confirmations.DownloadConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.DownloadInformation;
import com.xuxiaocheng.wlist.api.core.files.progresses.DownloadChunkProgress;
import com.xuxiaocheng.wlist.api.core.files.progresses.DownloadProgress;
import com.xuxiaocheng.wlist.api.core.files.tokens.DownloadToken;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

/**
 * The core download API.
 */
public enum Download {;
    /**
     * Download the file.
     * Note the download token will lock the file until it is canceled/finished.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file. ({@code assert !file.isDirectory;})
     * @param from the start byte index.
     * @param to the last byte index.
     * @return a future, with the download confirmation.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see java.lang.IllegalArgumentException
     */
    public static CompletableFuture<DownloadConfirmation> download(final CoreClient client, final String token, final FileLocation file, final long from, final long to) { return Main.future(); }

    /**
     * Cancel a download.
     * @param client the core client.
     * @param token the download token.
     * @return a future.
     */
    public static CompletableFuture<Void> cancel(final CoreClient client, final DownloadToken token) { return Main.future(); }

    /**
     * Confirm a download.
     * @param client the core client.
     * @param token the download token.
     * @return a future, with the download information.
     */
    public static CompletableFuture<DownloadInformation> confirm(final CoreClient client, final DownloadToken token) { return Main.future(); }

    /**
     * Real method to download the file.
     * Note that the buffer needn't be large enough to contain the entire chunk.
     * You can call this method multiple times to download the rest data.
     * This means you cannot download a chunk twice.
     * @param client the core client.
     * @param token the download token.
     * @param id the download chunk id.
     * @param buffer the directly buffer to write the data. ({@code assert buffer.isDirect();})
     * @return a future.
     */
    public static CompletableFuture<Void> chunk(final CoreClient client, final DownloadToken token, final int id, final ByteBuffer buffer) { return Main.future(); }

    /**
     * Finish a download.
     * @param client the core client.
     * @param token the download token.
     * @return a future.
     */
    public static CompletableFuture<Void> finish(final CoreClient client, final DownloadToken token) {
        return Download.cancel(client, token);
    }

    /**
     * Get the progress of download chunk.
     * @param client the core client.
     * @param token the download token.
     * @param id the download chunk id.
     * @return a future, with the progress of download chunk.
     */
    public static CompletableFuture<DownloadChunkProgress> progressChunk(final CoreClient client, final DownloadToken token, final int id) { return Main.future(); }

    /**
     * Get the progress of the whole download.
     * @param client the core client.
     * @param token the download token.
     * @return a future, with the progress of the whole download.
     */
    public static CompletableFuture<DownloadProgress> progress(final CoreClient client, final DownloadToken token) { return Main.future(); }
}
