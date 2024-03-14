package com.xuxiaocheng.wlist.api.web;

import com.xuxiaocheng.wlist.api.common.NetworkFuture;
import com.xuxiaocheng.wlist.api.common.Stable;
import com.xuxiaocheng.wlist.api.common.StableModule;

/**
 * The web version API.
 */
@Stable(since = "1.1.0", module = StableModule.Web)
public enum Version {
    /**
     * The current version is the latest and needn't upgrade.
     */
    Latest,

    /**
     * The current version is not the latest but still can be used.
     */
    Updatable,

    /**
     * The current version shouldn't be used and must upgrade.
     */
    Unavailable,
    ;

    /**
     * Check the core version.
     * @return a future, with the update information.
     * @see com.xuxiaocheng.wlist.api.common.Marks#CORE_IMPL_VERSION
     */
    public static native NetworkFuture<Version> checkCore();

    /**
     * Check the web version.
     * @return a future, with the update information.
     * @see com.xuxiaocheng.wlist.api.common.Marks#WEB_IMPL_VERSION
     */
    public static native NetworkFuture<Version> checkWeb();
}
