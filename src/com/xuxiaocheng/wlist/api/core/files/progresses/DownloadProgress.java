package com.xuxiaocheng.wlist.api.core.files.progresses;

/**
 * The progress of the whole download.
 * @param downloaded the downloaded bytes. (The total size has returned in {@link com.xuxiaocheng.wlist.api.core.files.confirmations.DownloadConfirmation})
 */
public record DownloadProgress(long downloaded) {
}
