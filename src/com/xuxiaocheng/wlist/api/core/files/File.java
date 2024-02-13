package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.beans.FileInformation;
import com.xuxiaocheng.wlist.api.core.files.beans.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.tokens.RefreshToken;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;
import io.jbock.util.Either;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The core file API.
 */
public enum File {;
    /**
     * List the files in directory.
     * @param client the core client.
     * @param token the core token.
     * @param directory the location of the directory. ({@code assert directory.isDirectory;})
     * @param options the options for the list operation.
     * @return a future, with the list result or the refresh token.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     */
    public static CompletableFuture<Either<FileListInformation, RefreshToken>> list(final CoreClient client, final String token, final FileLocation directory, final ListFileOptions options) { return Main.future(); }

    /**
     * Get the file/directory information.
     * @param client the core client.
     * @param token the core token.
     * @param file the location of the file/directory
     * @return a future, with the optional file/directory information.
     */
    public static CompletableFuture<Optional<FileInformation>> get(final CoreClient client, final String token, final FileLocation file) { return Main.future(); }

    /**
     * Refresh the directory.
     * Note the refresh token will lock the directory until it is canceled/finished.
     * @param client the core client.
     * @param token the core token.
     * @param directory the location of the directory. {@code assert directory.isDirectory;}
     * @return a future, with the refresh token.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     */
    public static CompletableFuture<RefreshToken> refresh(final CoreClient client, final String token, final FileLocation directory) { return Main.future(); }

    /**
     * Cancel a refresh.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future.
     */
    public static CompletableFuture<Void> cancelRefresh(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Confirm a refresh.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future. It will be complete after finishing, even it's paused.
     */
    public static CompletableFuture<Void> confirmRefresh(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Pause a refresh.
     * Note that if refresh has been paused, the method will return normally.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future.
     */
    public static CompletableFuture<Void> pauseRefresh(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Resume a refresh
     * Note that if refresh has been running, the method will return normally.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future.
     */
    public static CompletableFuture<Void> resumeRefresh(final CoreClient client, final RefreshToken token) { return Main.future(); }
}
