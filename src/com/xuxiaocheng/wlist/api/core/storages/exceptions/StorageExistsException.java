package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

public class StorageExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1211419147906466881L;

    public StorageExistsException() {
        super();
    }
}
