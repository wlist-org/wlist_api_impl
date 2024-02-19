package com.xuxiaocheng.wlist.api.core.storages.information;

import com.xuxiaocheng.wlist.api.Main;

import java.io.Serializable;
import java.util.List;

/**
 * The information list of a storage list.
 * @param total the total number of storages in the directory.
 * @param filtered the number of storages after filtering. (see {@link com.xuxiaocheng.wlist.api.core.storages.options.Filter}).
 * @param storages the information list.
 */
public record StorageListInformation(long total, long filtered, List<StorageInformation> storages) implements Serializable, AutoCloseable {
    @Override
    public void close() { throw Main.stub(); }
}
