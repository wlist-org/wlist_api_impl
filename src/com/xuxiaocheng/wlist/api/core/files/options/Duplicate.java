package com.xuxiaocheng.wlist.api.core.files.options;

/**
 * The policy when uploading a file/creating a directory that already exists.
 */
public enum Duplicate {
    /**
     * Throw {@link com.xuxiaocheng.wlist.api.core.files.exceptions.DuplicateFileException} directly.
     */
    Error,

    /**
     * Replace the old file/directory. (Not atomic, delete before uploading/creating.)
     */
    Replace,

    /**
     * Keep the old file/directory. Add a suffix to the new file/directory name.
     * The suffix may be "\u0020\u0028\u0031\u0029", "\u0020\u0028\u0032\u0029", "\uff08\u0031\uff09", etc.
     * <p>This may throw {@link com.xuxiaocheng.wlist.api.core.files.exceptions.NameTooLongException} even if the original name is short enough, because the suffix has been added.</p>
     */
    Rename,

    /**
     * Compare files/directories, use {@link com.xuxiaocheng.wlist.api.core.files.options.Duplicate#Rename} if different, otherwise ignore the upload/create.
     * <p>For file, compare both md5 and size of the file. If any md5/size is unknown, the files are different.</p>
     * <p>For directory, only two confirmed(have indexed) empty directories are same.</p>
     */
    RenameIfDifferent,
}
