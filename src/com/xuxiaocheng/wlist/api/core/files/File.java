package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.beans.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.confirm.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;
import io.jbock.util.Either;

import java.util.concurrent.CompletableFuture;

/**
 * The core file API.
 */
public enum File {;
    /**
     * List the files in directory.
     * @param client the core client.
     * @param token the core token.
     * @param directory the location of the directory. {@code assert directory.isDirectory;}
     * @param options the options for the list operation.
     * @return a future, with the list result or the refresh confirmation.
     */
    public static CompletableFuture<Either<FileListInformation, RefreshConfirmation>> list(final CoreClient client, final String token, final FileLocation directory, final ListFileOptions options) { return Main.future(); }
    
}
