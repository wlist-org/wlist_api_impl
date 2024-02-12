package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import java.io.Serial;

/**
 * Thrown when the storage account is incorrect.
 * Usually caused by the wrong username or password.
 */
public class IncorrectStorageAccountException extends InvalidStorageConfigException {
    @Serial
    private static final long serialVersionUID = 378567523489688490L;

    public IncorrectStorageAccountException() {
        super("password");
    }
}
