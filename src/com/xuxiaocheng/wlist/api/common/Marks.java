package com.xuxiaocheng.wlist.api.common;

/**
 * Some const version strings used in runtime.
 */
@SuppressWarnings("NativeMethod")
@Stable(since = "1.0.0", module = StableModule.Common)
public final class Marks {
    private Marks() {
        super();
    }

    /**
     * This flag will be true in a non-stub environment.
     */
    public static final boolean WITH_IMPL = true;

    private static native String getCommonImplVersion();
    /**
     * The common package version.
     */
    public static final String COMMON_API_VERSION = "1.5.0";
    /**
     * The common package impl version.
     */
    public static final String COMMON_IMPL_VERSION = Marks.getCommonImplVersion();

    private static native String getCoreImplVersion();
    /**
     * The core api version.
     */
    public static final String CORE_API_VERSION = "1.13.0";
    /**
     * The core api impl version.
     */
    public static final String CORE_IMPL_VERSION = Marks.getCoreImplVersion();


    private static native String getWebImplVersion();
    /**
     * The web api version.
     */
    public static final String WEB_API_VERSION = "1.1.0";
    /**
     * The web api impl version.
     */
    public static final String WEB_IMPL_VERSION = Marks.getWebImplVersion();
}
