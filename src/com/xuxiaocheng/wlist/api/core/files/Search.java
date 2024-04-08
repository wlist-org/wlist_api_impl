package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.information.FileListInformation;
import com.xuxiaocheng.wlist.api.core.files.options.ListFileOptions;
import com.xuxiaocheng.wlist.api.core.files.options.SearchOptions;

import java.util.concurrent.CompletableFuture;

/**
 * The core search API.
 * Note that only indexed files/directories can be searched.
 */
public enum Search {;
    /**
     * Search in all the storages.
     * @param client the core client.
     * @param token the core token.
     * @param options the options for the search operation.
     * @param list the options for the list operation.
     * @return a future, with the searched result.
     */
    public static CompletableFuture<FileListInformation> global(final CoreClient client, final String token, final SearchOptions options, final ListFileOptions list) { return Main.future(); }

    /**
     * Search in a specified directory.
     * @param client the core client.
     * @param token the core token.
     * @param directory the specified directory.
     * @param options the options for the search operation.
     * @param list the options for the list operation.
     * @return a future, with the searched result.
     */
    public static CompletableFuture<FileListInformation> search(final CoreClient client, final String token, final FileLocation directory, final SearchOptions options, final ListFileOptions list) { return Main.future(); }
}
