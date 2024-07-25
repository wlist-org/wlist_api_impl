package com.xuxiaocheng.wlist.api.graal;

import org.graalvm.nativeimage.c.CContext;

import java.util.List;

public final class BasicDirective implements CContext.Directives {
    @Override
    public List<String> getLibraries() {
        return List.of("ntdll", "basic");
    }

    @Override
    public List<String> getLibraryPaths() {
        return List.of("basic/target/release");
    }

    @Override
    public List<String> getHeaderFiles() {
        return List.of("\"basic/bindings.h\"");
    }
}
