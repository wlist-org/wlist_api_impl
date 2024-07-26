package com.xuxiaocheng.wlist.api.graal;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.word.UnsignedWord;
import org.graalvm.word.WordFactory;

import java.nio.charset.StandardCharsets;

@CContext(BasicDirective.class)
public final class Basic {
    private Basic() {
        super();
    }

    public static void main(final String[] args) {
    }

    @CFunction("allocate_cstring")
    private static native CCharPointer allocateString(final UnsignedWord length);

    @CFunction("free_cstring")
    private static native void freeString(final CCharPointer pointer, final UnsignedWord length);
    @CEntryPoint(name = "free_string")
    public static void freeString(@SuppressWarnings("unused") final IsolateThread thread, final CCharPointer pointer, final UnsignedWord length) {
        Basic.freeString(pointer, length);
    }

    public static CCharPointer toCString(final CharSequence string) {
        final UnsignedWord length = CTypeConversion.toCString(string, StandardCharsets.UTF_8, WordFactory.nullPointer(), WordFactory.zero()).add(1);
        final CCharPointer pointer = Basic.allocateString(length);
        CTypeConversion.toCString(string, StandardCharsets.UTF_8, pointer, length);
        return pointer;
    }

    public static String fromCString(final CCharPointer ptr) {
        return CTypeConversion.utf8ToJavaString(ptr);
    }
}
