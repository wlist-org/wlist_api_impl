package com.xuxiaocheng.wlist.api.impl.helper;

import org.htmlunit.ElementNotFoundException;
import org.htmlunit.WebClient;
import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;
import org.htmlunit.WebResponseData;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.HtmlAnchor;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlInlineFrame;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSpan;
import org.htmlunit.javascript.host.event.MouseEvent;
import org.htmlunit.util.Cookie;
import org.htmlunit.util.WebConnectionWrapper;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("SpellCheckingInspection")
public enum Lanzou {;
    public record Tokens(String token, Instant expire, long uid) {}

    public static Optional<Tokens> login(final String passport, final String password) throws IOException {
        final Set<Cookie> cookies;
        try (final WebClient client = BrowserUtil.newWebClient()) {
            final HtmlPage page = client.getPage("https://up.woozooo.com/account.php?action=login");
            BrowserUtil.waitJavaScriptCompleted(client);
            final HtmlSpan slide = page.getHtmlElementById("nc_1_n1z");
            slide.mouseDown();
            slide.mouseMove(false, false, false, MouseEvent.BUTTON_RIGHT);
            slide.mouseUp();
            page.<HtmlInput>getElementByName("username").setValue(passport);
            page.<HtmlInput>getElementByName("password").setValue(password);
            page.getHtmlElementById("s3").click();
            cookies = client.getCookies(URI.create("https://up.woozooo.com/").toURL());
        }
        Cookie token = null, uid = null;
        for (final Cookie c: cookies) {
            if ("phpdisk_info".equalsIgnoreCase(c.getName()))
                token = c;
            if ("ylogin".equalsIgnoreCase(c.getName()))
                uid = c;
        }
        if (token == null || uid == null)
            return Optional.empty();
        return Optional.of(new Tokens(token.getValue(), token.getExpires().toInstant(), Long.parseLong(uid.getValue())));
    }


    public record FileUrls(boolean available, boolean passwordMatched, String url) {}

    public static FileUrls getSingleShareFileDownloadUrl(final String urlString, final String pwd) throws IOException {
        final URL url = URI.create(urlString).toURL();
        final HtmlElement downloading;
        try (final WebClient client = BrowserUtil.newWebClient()) {
            client.setWebConnection(new WebConnectionWrapper(client.getWebConnection()) {
                private static final WebResponseData QRCodeMinJs = new WebResponseData("var QRCode=function(a,b){};QRCode.CorrectLevel={L:1,M:0,Q:3,H:2};".getBytes(StandardCharsets.UTF_8), 200, "OK", List.of(BrowserUtil.JSResponseHeader));
                @Override
                public WebResponse getResponse(final WebRequest request) throws IOException {
                    final URL requestUrl = request.getUrl();
                    final String path = requestUrl.getPath();
                    if (path.endsWith("/qrcode.min.js")) // skip QR code
                        return new WebResponse(QRCodeMinJs, request, 0);
                    if (path.endsWith("/bd.js") || path.endsWith("/hm.js")) // skip hm
                        return BrowserUtil.emptyJSResponse(request);
                    if (!requestUrl.getHost().equals(url.getHost()) && !requestUrl.getHost().equals("assets.woozooo.com")) {
                        // skip other unnessary asserts
                        if (path.endsWith(".js"))
                            return BrowserUtil.emptyJSResponse(request);
                        return BrowserUtil.emptyResponse(request);
                    }
                    return super.getResponse(request);
                }
            });
            final HtmlPage page = client.getPage(url);
            // check share file exists
            try {
                page.getElementByName("description");
            } catch (final ElementNotFoundException ignore) {
                return new FileUrls(false, false, null);
            }
            // check password
            HtmlInput input;
            try {
                input = page.getElementByName("pwd");
            } catch (final ElementNotFoundException ignore) {
                input = null;
            }
            if (input != null) {
                // need password
                if (pwd == null)
                    return new FileUrls(true, false, null);
                input.setValue(pwd);
                page.<DomElement>querySelector(".passwddiv-btn").click();
                BrowserUtil.waitJavaScriptCompleted(client);
                if (page.getHtmlElementById("passwddiv").getStyleElement("display") == null) // require "none"
                    return new FileUrls(true, false, null);
                downloading = page.getHtmlElementById("downajax");
            } else {
                // no password
                BrowserUtil.waitStartedJavaScriptCompleted(client);
                final HtmlPage frame = (HtmlPage) page.<HtmlInlineFrame>querySelector(".ifr2").getEnclosedPage();
                downloading = frame.getHtmlElementById("tourl");
            }
        }
        final String downloadUrl = downloading.<HtmlAnchor>querySelector("a").getAttribute("href");
        return new FileUrls(true, true, downloadUrl);
    }
}
