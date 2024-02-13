/**
 * The api of wlist project.
 */
module com.xuxiaocheng.wlist.api {
//    requires java.base;
    requires io.jbock.util;

    exports com.xuxiaocheng.wlist.api;
    exports com.xuxiaocheng.wlist.api.exceptions;
    exports com.xuxiaocheng.wlist.api.core;
    exports com.xuxiaocheng.wlist.api.core.exceptions;
    exports com.xuxiaocheng.wlist.api.core.storages;
    exports com.xuxiaocheng.wlist.api.core.storages.exceptions;
    exports com.xuxiaocheng.wlist.api.core.storages.configs;
    exports com.xuxiaocheng.wlist.api.core.files;
    exports com.xuxiaocheng.wlist.api.core.files.exceptions;
    exports com.xuxiaocheng.wlist.api.core.files.options;
    exports com.xuxiaocheng.wlist.api.core.files.beans;
    exports com.xuxiaocheng.wlist.api.core.files.tokens;
    exports com.xuxiaocheng.wlist.api.core.broadcast;
    exports com.xuxiaocheng.wlist.api.core.broadcast.events;
    exports com.xuxiaocheng.wlist.api.web;
    exports com.xuxiaocheng.wlist.api.web.exceptions;
}
