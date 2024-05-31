package com.xuxiaocheng.wlist.api.core.storages.types;

import com.xuxiaocheng.wlist.api.core.storages.configs.LanzouConfig;
import com.xuxiaocheng.wlist.api.impl.ClientStarter;
import com.xuxiaocheng.wlist.api.impl.enums.Functions;

import java.util.Set;

/**
 * The core lanzou storage API.
 */
public enum Lanzou implements StorageType<LanzouConfig> {
    /** The instance. */ Instance;

    @Override
    public Functions functionAdd() {
        return Functions.StorageLanzouAdd;
    }

    @Override
    public Functions functionUpdate() {
        return Functions.StorageLanzouUpdate;
    }

    @Override
    public Functions functionCheck() {
        return Functions.StorageLanzouCheckConfig;
    }

    @Override
    public ClientStarter.PackFunction configPacker(final LanzouConfig config) {
        return packer -> LanzouConfig.serialize(config, packer);
    }


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
