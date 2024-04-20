package com.xuxiaocheng.wlist.api.core.storages.options;

import com.xuxiaocheng.wlist.api.common.Direction;
import com.xuxiaocheng.wlist.api.common.Recyclable;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Options when listing storages.
 * @param filter the filter to determine which type of storage to list.
 * @param orders the order in which to list the storage. (The front entry has a higher priority.)
 *               (Note that items of the same priority will be listed in random order.)
 * @param offset the offset of the first item to list.
 * @param limit the maximum number of items to list.
 */
public record ListStorageOptions(Filter filter, LinkedHashMap<Order, Direction> orders, long offset, int limit)
        implements Serializable, Recyclable {
    public static void serialize(final ListStorageOptions self, final MessagePacker packer) throws IOException {
        packer.packString(self.filter.name());
        packer.packMapHeader(self.orders.size());
        for (final Map.Entry<Order, Direction> order: self.orders.entrySet()) {
            packer.packString(order.getKey().name());
            packer.packString(order.getValue().name());
        }
        packer.packLong(self.offset);
        packer.packInt(self.limit);
    }

    public static ListStorageOptions deserialize(final MessageUnpacker unpacker) throws IOException {
        final Filter filter = Filter.valueOf(unpacker.unpackString());
        final int size = unpacker.unpackMapHeader();
        final LinkedHashMap<Order, Direction> orders = new LinkedHashMap<>(size);
        for (int i = 0; i < size; ++i)
            orders.put(Order.valueOf(unpacker.unpackString()), Direction.valueOf(unpacker.unpackString()));
        final long offset = unpacker.unpackLong();
        final int limit = unpacker.unpackInt();
        return new ListStorageOptions(filter, orders, offset, limit);
    }
}
