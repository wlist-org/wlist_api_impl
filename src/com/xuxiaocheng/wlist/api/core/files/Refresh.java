package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.common.NetworkFuture;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.progresses.RefreshProgress;
import com.xuxiaocheng.wlist.api.core.files.tokens.RefreshToken;

/**
 * The core refresh API.
 */
public enum Refresh {;
    /**
     * Refresh the directory.
     * Note the refresh token will lock the directory until it is canceled/finished.
     * @param client the core client.
     * @param token the core token.
     * @param directory the location of the directory. ({@code assert directory.isDirectory;})
     * @return a future, with the refresh confirmation.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     */
    public static NetworkFuture<RefreshConfirmation> refresh(final CoreClient client, final String token, final FileLocation directory) { return Main.future(); }

    /**
     * Cancel a refresh.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future.
     */
    public static NetworkFuture<Void> cancel(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Confirm a refresh.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future. It will be complete after finishing, even it's paused.
     */
    public static NetworkFuture<Void> confirm(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Pause a refresh.
     * Note that if refresh has been paused, the method will return normally.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future.
     */
    public static NetworkFuture<Void> pause(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Resume a refresh
     * Note that if refresh has been running, the method will return normally.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future.
     */
    public static NetworkFuture<Void> resume(final CoreClient client, final RefreshToken token) { return Main.future(); }

    /**
     * Get the progress of refresh.
     * Note that if the refresh is finished/canceled, it will complete exceptionally with {@link com.xuxiaocheng.wlist.api.exceptions.TokenExpiredException}.
     * @param client the core client.
     * @param token the refresh token.
     * @return a future, with the progress of refresh.
     */
    public static NetworkFuture<RefreshProgress> progress(final CoreClient client, final RefreshToken token) { return Main.future(); }
}
