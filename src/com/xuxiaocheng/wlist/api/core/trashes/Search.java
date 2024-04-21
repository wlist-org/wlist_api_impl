package com.xuxiaocheng.wlist.api.core.trashes;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.trashes.information.TrashListInformation;
import com.xuxiaocheng.wlist.api.core.trashes.options.ListTrashOptions;
import com.xuxiaocheng.wlist.api.core.trashes.options.SearchOptions;

import java.util.concurrent.CompletableFuture;

public enum Search {;
    /**
     * Search trashed file/directories in all the storages.
     * @param client the core client.
     * @param token the core token.
     * @param options the options for the search operation.
     * @param list the options for the list operation.
     * @return a future, with the searched result.
     */
    public static CompletableFuture<TrashListInformation> global(final CoreClient client, final String token, final SearchOptions options, final ListTrashOptions list) { return Main.future(); }

    /**
     * Search trashed file/directories in a specified storage.
     * @param client the core client.
     * @param token the core token.
     * @param storage the specified storage.
     * @param options the options for the search operation.
     * @param list the options for the list operation.
     * @return a future, with the searched result.
     */
    public static CompletableFuture<TrashListInformation> search(final CoreClient client, final String token, final long storage, final SearchOptions options, final ListTrashOptions list) { return Main.future(); }
}
