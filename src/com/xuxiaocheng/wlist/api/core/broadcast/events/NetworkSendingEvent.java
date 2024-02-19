package com.xuxiaocheng.wlist.api.core.broadcast.events;

/**
 * An event that informs that the server is sending to network.
 * @param url the url server access. (Not real, without any params and key information.)
 * @param id the id of the sending request.
 */
public record NetworkSendingEvent(String url, long id) implements Event {
}
