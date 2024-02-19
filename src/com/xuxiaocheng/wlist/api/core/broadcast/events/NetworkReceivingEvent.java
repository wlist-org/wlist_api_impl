package com.xuxiaocheng.wlist.api.core.broadcast.events;

/**
 * An event that informs that the server is receiving from network.
 * @param id the id of the sending request.
 */
public record NetworkReceivingEvent(long id) implements Event {
}
