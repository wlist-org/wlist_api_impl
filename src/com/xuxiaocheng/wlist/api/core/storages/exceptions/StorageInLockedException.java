package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

public class StorageInLockedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4737443365855387892L;

    public StorageInLockedException(final String message) {
        super(message);
    }
}
