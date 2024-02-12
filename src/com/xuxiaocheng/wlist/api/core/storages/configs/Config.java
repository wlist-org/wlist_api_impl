package com.xuxiaocheng.wlist.api.core.storages.configs;

import java.io.Serializable;

/**
 * An interface that represents a config.
 */
public sealed interface Config extends Serializable permits LanzouConfig {
}
