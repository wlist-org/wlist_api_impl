package com.xuxiaocheng.wlist.api.core.trashes.information;

import com.xuxiaocheng.wlist.api.common.Recyclable;

import java.io.Serializable;

/**
 * The detail information of a trashed file/directory.
 * @param basic the basic information.
 * @param optionalMd5 the optional(<b>nullable</b>) md5. (This is a lowercase string with a length of 32.) (For directory, it's always null.)
 */
public record TrashDetailsInformation(TrashInformation basic, String optionalMd5)
        implements Serializable, Recyclable {
}
