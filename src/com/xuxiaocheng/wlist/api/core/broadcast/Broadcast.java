package com.xuxiaocheng.wlist.api.core.broadcast;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.broadcast.events.Event;

import java.util.concurrent.CompletableFuture;

/**
 * The core broadcast API.
 */
public enum Broadcast {;
    /**
     * Convert a core client as a broadcast receiver.
     * You <b>must</b> close the receiver if you no longer need it to prevent resource leak.
     * @param client the core client.
     * @param token the core token.
     * @return a future, with a broadcast receiver.
     */
    public static CompletableFuture<BroadcastClient> receiver(final CoreClient client, final String token) { throw Main.stub(); }

    /**
     * Receive the next message.
     * After handling the event, you should call this method again to receive the next event.
     * @param client the broadcast receiver.
     * @return a future, with the next event.
     */
    public static CompletableFuture<Event> next(final BroadcastClient client) { throw Main.stub(); }
}
