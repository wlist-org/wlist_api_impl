package com.xuxiaocheng.wlist.api.impl.helper;

import io.netty.util.internal.EmptyArrays;
import org.htmlunit.BrowserVersion;
import org.htmlunit.Cache;
import org.htmlunit.SilentCssErrorHandler;
import org.htmlunit.WebClient;
import org.htmlunit.WebConsole;
import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;
import org.htmlunit.WebResponseData;
import org.htmlunit.javascript.SilentJavaScriptErrorListener;
import org.htmlunit.util.NameValuePair;

import java.util.List;

public final class BrowserUtil {
    private BrowserUtil() {
        super();
    }

    private static final Cache SharedCache = new Cache(); static {
        SharedCache.setMaxSize(64);
    }

    public static WebClient newWebClient() {
        final WebClient client = new WebClient(BrowserVersion.EDGE);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.setCache(BrowserUtil.SharedCache);
        client.setIncorrectnessListener((message, origin) -> {/*Ignore*/});
        client.setCssErrorHandler(new SilentCssErrorHandler());
        client.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
        client.getWebConsole().setLogger(new SilentConsoleLogger());
        return client;
    }

    public static class SilentConsoleLogger implements WebConsole.Logger {
        @Override public boolean isTraceEnabled() { return false; } @Override public void trace(final Object message) { }
        @Override public boolean isDebugEnabled() { return false; } @Override public void debug(final Object message) { }
        @Override public boolean isInfoEnabled() { return false; } @Override public void info(final Object message) { }
        @Override public boolean isWarnEnabled() { return false; } @Override public void warn(final Object message) { }
        @Override public boolean isErrorEnabled() { return false; } @Override public void error(final Object message) { }
    }


    public static void waitJavaScriptCompleted(final WebClient client) {
        while (true)
            if (client.waitForBackgroundJavaScript(5000) == 0)
                break;
    }

    public static void waitJavaScriptLeft(final WebClient client, final int left) {
        while (true)
            if (client.waitForBackgroundJavaScript(300) <= left)
                break;
    }


    public static final NameValuePair JSResponseHeader = new NameValuePair("Content-Type", "application/javascript");

    public static final WebResponseData EmptyResponse = new WebResponseData(EmptyArrays.EMPTY_BYTES, 200, "OK", List.of());
    public static final WebResponseData EmptyJSResponse = new WebResponseData(EmptyArrays.EMPTY_BYTES, 200, "OK", List.of(BrowserUtil.JSResponseHeader));

    public static WebResponse emptyResponse(final WebRequest request) {
        return new WebResponse(BrowserUtil.EmptyResponse, request, 0);
    }
    public static WebResponse emptyJSResponse(final WebRequest request) {
        return new WebResponse(BrowserUtil.EmptyJSResponse, request, 0);
    }
}
