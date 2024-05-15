package com.xuxiaocheng.wlist.api.core.files.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;
import java.time.Instant;

/**
 * The share information of the files/directories.
 * @param id the sharing id.
 * @param optionalPassword the optional(<b>nullable</b>) password.
 * @param expire the expiry time.
 */
public record ShareInformation(String id, String optionalPassword, Instant expire)
        implements Serializable, Recyclable {
}
