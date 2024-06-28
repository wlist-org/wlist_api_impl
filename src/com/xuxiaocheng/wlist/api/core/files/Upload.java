package com.xuxiaocheng.wlist.api.core.files;

import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.files.confirmations.UploadConfirmation;
import com.xuxiaocheng.wlist.api.core.files.information.FileInformation;
import com.xuxiaocheng.wlist.api.core.files.information.UploadInformation;
import com.xuxiaocheng.wlist.api.core.files.options.Duplicate;
import com.xuxiaocheng.wlist.api.core.files.tokens.UploadToken;
import com.xuxiaocheng.wlist.api.impl.ClientStarter;
import com.xuxiaocheng.wlist.api.impl.data.InternalUploadInformation;
import com.xuxiaocheng.wlist.api.impl.data.InternalUploadInformationCallback;
import com.xuxiaocheng.wlist.api.impl.data.UploadInformationExtra;
import com.xuxiaocheng.wlist.api.impl.enums.Functions;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The core upload API.
 */
public enum Upload {;
    /**
     * Request to upload a new file.
     * If the returned {@code confirmation.done} is true, you should call {@link com.xuxiaocheng.wlist.api.core.files.Upload#finish(com.xuxiaocheng.wlist.api.core.CoreClient, com.xuxiaocheng.wlist.api.core.files.tokens.UploadToken)} directly.
     * @param client the core client.
     * @param token the core token.
     * @param parent the parent directory. ({@code assert parent.isDirectory();})
     * @param name the name of the new file.
     * @param size the size of the entire new file.
     * @param md5 the hash md5 of the entire new file. (This should be a lowercase string with a length of 32.)
     * @param md5s the md5 slice of each 4MB part of the new file.
     * @param duplicate duplication policy of the new file.
     * @return a future, with the upload confirmation.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.FileNotFoundException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.SpaceNotEnoughException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.FlowNotEnoughException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.FileTooLargeException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.NameTooLongException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.InvalidFilenameException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.IllegalSuffixException
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.limitations.ReadOnlyStorageException
     */
    public static CompletableFuture<UploadConfirmation> request(final CoreClient client, final String token, final FileLocation parent, final String name, final long size, final String md5, final String[] md5s, final Duplicate duplicate) {
        return ClientStarter.client(client, Functions.UploadRequest, packer -> {
            packer.packString(token);
            FileLocation.serialize(parent, packer);
            packer.packString(name);
            packer.packLong(size);
            packer.packString(name);
            packer.packString(md5);
            packer.packArrayHeader(md5s.length);
            for (final String s: md5s)
                packer.packString(s);
            packer.packString(duplicate.name());
        }, UploadConfirmation::deserialize);
    }

    /**
     * Cancel a upload.
     * @param client the core client.
     * @param token the upload token.
     * @return a future.
     */
    public static CompletableFuture<Void> cancel(final CoreClient client, final UploadToken token) {
        return ClientStarter.client(client, Functions.UploadCancel, packer -> UploadToken.serialize(token, packer), ClientStarter::deserializeVoid)
                .thenCompose(ignored -> com.xuxiaocheng.wlist.api.impl.functions.Upload.cancelClient(token));
    }

    /**
     * Confirm a upload.
     * @param client the core client.
     * @param token the upload token.
     * @return a future, with the upload confirmation.
     */
    public static CompletableFuture<UploadInformation> confirm(final CoreClient client, final UploadToken token) {
        return ClientStarter.client(client, Functions.UploadConfirm, packer -> UploadToken.serialize(token, packer), UploadInformationExtra::deserialize)
                .thenCompose(extra -> com.xuxiaocheng.wlist.api.impl.functions.Upload.confirmClient(token, extra).thenApply(ignored -> extra.information()));
    }

    /**
     * Upload the file chunk.
     * Note that the buffer needn't be large enough to contain the entire chunk.
     * You can call this method multiple times to upload the rest data,
     * but please ensure that the buffer start position corresponds to the previous end position.
     * <p>You can call {@code buffer.position()} to get the uploading progress. (Not real-time, but at a small interval. Maybe hundreds to thousands of bytes)</p>
     * <p>If the returned hash isn't matched, you may cancel the upload and request a new one.</p>
     * <p>You can set the controller to pause or resume the upload.</p>
     * @param client the core client.
     * @param token the upload token.
     * @param id the upload chunk id.
     * @param buffer the buffer containing the chunk data.
     * @param controller false means pause, true means resume.
     * @return a future, with a sha256 hash of the uploaded chunk data.
     *                   The hash is optional because the chunk may upload incompletely.
     */
    public static CompletableFuture<Optional<String>> upload(final CoreClient client, final UploadToken token, final int id, final ByteBuffer buffer, final AtomicBoolean controller) {
        return CompletableFuture.completedFuture(null)
                .thenCompose(ignored -> ClientStarter.client(client, Functions.UploadUpload, packer -> {
                    UploadToken.serialize(token, packer);
                    packer.packInt(id);
                }, InternalUploadInformation::deserialize))
                .thenCompose(information -> com.xuxiaocheng.wlist.api.impl.functions.Upload.uploadClient(token, information, buffer, controller))
                .thenCompose(callback -> ClientStarter.client(client, Functions.UploadUploadCallback, packer -> {
                    UploadToken.serialize(token, packer);
                    packer.packInt(id);
                    InternalUploadInformationCallback.serialize(callback, packer);
                }, ClientStarter::deserializeVoid).thenApply(ignored -> Optional.ofNullable(callback.optionalSha256())));
    }

    /**
     * Finish an upload.
     * @param client the core client.
     * @param token the upload token.
     * @return a future, with the information of the new file.
     * @see com.xuxiaocheng.wlist.api.core.files.exceptions.UploadChunkIncompleteException
     */
    public static CompletableFuture<FileInformation> finish(final CoreClient client, final UploadToken token) {
        return ClientStarter.client(client, Functions.UploadFinish, packer -> UploadToken.serialize(token, packer), FileInformation::deserialize)
                .thenCompose(information -> com.xuxiaocheng.wlist.api.impl.functions.Upload.cancelClient(token).thenApply(ignored -> information));
    }
}
