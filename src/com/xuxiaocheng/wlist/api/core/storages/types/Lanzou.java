package com.xuxiaocheng.wlist.api.core.storages.types;

import com.xuxiaocheng.wlist.api.Main;
import com.xuxiaocheng.wlist.api.core.CoreClient;
import com.xuxiaocheng.wlist.api.core.storages.configs.LanzouConfig;
import com.xuxiaocheng.wlist.api.core.storages.information.StorageInformation;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * The core lanzou storage API.
 */
public enum Lanzou implements StorageType<LanzouConfig> {
    /** The instance. */ Instance;

    @Override
    public CompletableFuture<StorageInformation> add(final CoreClient client, final String token, final String storage, final LanzouConfig config) { return Main.future(); }

    @Override
    public CompletableFuture<Void> update(final CoreClient client, final String token, final long storage, final LanzouConfig config) { return Main.future(); }

    @Override
    public CompletableFuture<Void> checkConfig(final CoreClient client, final String token, final LanzouConfig config) { return Main.future(); }

    @Override
    public boolean isPrivate() {
        return true;
    }

    @Override
    public Set<String> allowedSuffixes() {
        //noinspection SpellCheckingInspection
        return Set.of(
                "doc","docx","zip","rar","apk","ipa","txt","exe","7z","e","z","ct","ke","cetrainer","db","tar","pdf","w3x",
                "epub","mobi","azw","azw3","osk","osz","xpa","cpk","lua","jar","dmg","ppt","pptx","xls","xlsx","mp3",
                /*"ipa",*/"iso","img","gho","ttf","ttc","txf","dwg","bat","imazingapp","dll","crx","xapk","conf",
                "deb","rp","rpm","rplib","mobileconfig","appimage","lolgezi","flac",
                "cad","hwt","accdb","ce","xmind","enc","bds","bdi","ssf","it",
                "pkg","cfg"
        );
    }

    @Override
    public Set<Character> disallowedCharacter() {
        return Set.of('/', '\\', '*', '|', '#', '$', '%', '^', '(', ')', '?', ':', '\'', '"', '`', '=', '+', '<', '>', ';', ',');
    }

    @Override
    public long minFilenameLength() {
        return 1;
    }

    @Override
    public long maxFilenameLength() {
        return 100;
    }
}
