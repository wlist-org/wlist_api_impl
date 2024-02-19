package com.xuxiaocheng.wlist.api.core.broadcast;

import com.xuxiaocheng.wlist.api.common.Recyclable;
import com.xuxiaocheng.wlist.api.core.CoreClient;

/**
 * Use {@link com.xuxiaocheng.wlist.api.core.broadcast.Broadcast#receiver(com.xuxiaocheng.wlist.api.core.CoreClient, String)} to create an instance.
 * @param client internal client.
 * @param token internal token.
 */
public record BroadcastClient(CoreClient client, String token) implements Recyclable {
}
