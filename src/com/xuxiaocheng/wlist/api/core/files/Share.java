package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.information.ShareInformation;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The core share API.
 */
public enum Share {;
    /**
     * Share the files/directories, these files/directories may not in a same directory even a same storage.
     * If {@code requirePassword} is true, the share information will contain the password.
     * If {@code optionalPassword} is null, or it's length isn't 4, the password will be auto-generated.
     * Note if {@code requirePassword} is false, {@code optionalPassword} will be ignored.
     * @param client the core client.
     * @param token the core token.
     * @param files the list of files/directories you want to share.
     * @param requirePassword true if you want to require a password to access the shared files/directories.
     * @param optionalPassword the password you want to set.
     * @return a future, with the sharing information.
     */
    public static CompletableFuture<ShareInformation> share(final CoreClient client, final String token, final List<FileLocation> files, final boolean requirePassword, final String optionalPassword) { return Main.future(); }
}
