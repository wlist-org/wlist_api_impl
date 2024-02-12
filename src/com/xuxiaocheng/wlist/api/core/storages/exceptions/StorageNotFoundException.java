package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

public class StorageNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6368257552600219142L;

    public StorageNotFoundException() {
        super();
    }
}
