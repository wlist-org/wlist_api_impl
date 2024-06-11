package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.Stable;
import com.xuxiaocheng.wlist.api.common.StableModule;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.progresses.RefreshProgress;
import com.xuxiaocheng.wlist.api.core.files.tokens.RefreshToken;

import java.util.concurrent.CompletableFuture;

/**
 * The core refresh API.
 * Only after refreshing, the files/directories are indexed.
 * <p>For a normal example: <pre> {@code
 * final RefreshConfirmation confirmation = Refresh.refresh(client, token, location).get();
 * Refresh.confirm(client, confirmation.token()).get();
 * while (true) {
 *   try {
 *     final RefreshProgress ignoredProgress = Refresh.progress(client, confirmation.token()).get();
 *     TimeUnit.SECONDS.sleep(1);
 *   } catch (final ExecutionException exception) {
 *     if (exception.getCause() instanceof TokenExpiredException)
 *       break;
 *     throw exception;
 *   }
 * }
 * final boolean finished = Refresh.check(client, confirmation.token()).get();
 * // Assertions.assertTrue(finished);
 * }</pre>
 */
@Stable(since = "1.18.0", module = StableModule.Core)
public enum Refresh {;
    /**
     * Refresh the directory.
     * Note that the refresh token will lock the directory until it is canceled/finished.
     * @param client the core client.
     * @param token the core token.
     * @param directory the location of the directory. ({@code assert directory.isDirectory;})
     * @return a future, with the refresh confirmation.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     */
    public static CompletableFuture<RefreshConfirmation> refresh(final CoreClient client, final String token, final FileLocation directory) { return Main.future(); }

    /**
     * Cancel a refresh.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<Void> cancel(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Confirm a refresh. (Then the refresh is automatically resumed.)
     * Note only after being confirmed, the refresh token will be valid (not expired) (except cancel).
     * @param client the core client.
     * @param token the refresh token.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<Void> confirm(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Pause a refresh.
     * Note that if refresh has been paused, the method will return normally.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<Void> pause(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Resume a refresh
     * Note that if refresh has been resumed, the method will return normally.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<Void> resume(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Check whether a refresh is paused.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<Boolean> isPaused(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Get the progress of refresh.
     * Note that if the refresh is finished/canceled, it will complete exceptionally with {@link com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException}.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future, with the progress of refresh.
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<RefreshProgress> progress(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Check the refresh state.
     * If the refresh progress throws any exceptions, it will complete exceptionally.
     * Note you should call this method to release resources after {@link #progress} throws {@code TokenExpiredException}.
     * If this method returned true, the next time call will throw {@code TokenExpiredException}.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future, true means the refresh is finished, false means the refresh is in progress (or be paused).
     * @see com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException
     */
    public static CompletableFuture<Boolean> check(final CoreClient client, final RefreshToken token) { return Main.future(); }
}
