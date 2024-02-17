package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.confirmations.DownloadConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.DownloadInformation;
import com.xuxiaocheng.wlist.api.core.files.tokens.DownloadToken;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

/**
 * The core download API.
 */
public enum Download {;
    /**
     * Request to download the file.
     * Note the download token will lock the file until it is canceled/finished.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file. ({@code assert !file.isDirectory;})
     * @param from the start byte index of the entire file.
     * @param to the last byte index of the entire file. (For entire file, you can pass {@link java.lang.Long#MAX_VALUE}.)
     * @return a future, with the download confirmation.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.FlowNotEnoughException
     * @see java.lang.IllegalArgumentException
     */
    public static CompletableFuture<DownloadConfirmation> request(final CoreClient client, final String token, final FileLocation file, final long from, final long to) { return Main.future(); }

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
     * Download the file chunk.
     * Note that the buffer needn't be large enough to contain the entire chunk.
     * This method will start downloading from the {@code start} byte
     * and try to fill the {@code buffer} as much as possible.
     * <p>The behavior of this method is similar to sending a GET request containing a range header.</p>
     * <p>You can call {@code buffer.position()} to get the downloading progress. (Not real-time, but at a small interval. Maybe hundreds to thousands of bytes)</p>
     * @param client the core client.
     * @param token the download token.
     * @param id the download chunk id.
     * @param buffer the directly buffer to write the data. ({@code assert buffer.isDirect();})
     * @param start the start position to download of the <b>chunk</b>.
     * @return a future.
     */
    public static CompletableFuture<Void> download(final CoreClient client, final DownloadToken token, final int id, final ByteBuffer buffer, final long start) { return Main.future(); }

    /**
     * Finish a download.
     * This method is similar to call {@link com.xuxiaocheng.wlist.api.core.files.Download#cancel(com.xuxiaocheng.wlist.api.core.CoreClient, com.xuxiaocheng.wlist.api.core.files.tokens.DownloadToken)},
     * but different in log.
     * @param client the core client.
     * @param token the download token.
     * @return a future.
     */
    public static CompletableFuture<Void> finish(final CoreClient client, final DownloadToken token) { return Main.future(); }
}
