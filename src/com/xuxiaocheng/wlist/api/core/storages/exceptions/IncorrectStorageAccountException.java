package com.xuxiaocheng.wlist.api.core.storages.exceptions;

import com.xuxiaocheng.wlist.api.impl.enums.Exceptions;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serial;
import java.util.Map;

/**
 * Thrown when the storage account is incorrect.
 * Usually caused by the wrong username or password.
 */
public class IncorrectStorageAccountException extends InvalidStorageConfigException implements Exceptions.CustomExceptions {
    @Serial
    private static final long serialVersionUID = 378567523489688490L;

    /**
     * Internal constructor.
     */
    private IncorrectStorageAccountException() {
        super(Map.of("password", "Incorrent storage account."));
    }

    @Override
    public Exceptions identifier() {
        return Exceptions.IncorrectStorageAccount;
    }

    @Override
    public void serialize(final MessagePacker packer) throws IOException {
    }

    public static IncorrectStorageAccountException deserialize(final MessageUnpacker ignoredUnpacker) throws IOException {
        return new IncorrectStorageAccountException();
    }
}
