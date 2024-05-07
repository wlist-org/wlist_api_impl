package com.xuxiaocheng.wlist.api.impl.helper;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSpan;
import org.htmlunit.javascript.host.event.MouseEvent;
import org.htmlunit.util.Cookie;

import java.io.IOException;
import java.net.URI;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Set;

public enum Lanzou {;
    public record Tokens(String token, long uid, ZonedDateTime expires) {}

    // Nullable
    public static Tokens login(final String passport, final String password) throws IOException {
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
            return null;
        final ZonedDateTime expires = ZonedDateTime.ofInstant(token.getExpires().toInstant(), ZoneOffset.UTC);
        return new Tokens(token.getValue(), Long.parseLong(uid.getValue()), expires);
    }
}
