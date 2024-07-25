package com.xuxiaocheng.wlist.api.graal;

import org.graalvm.nativeimage.UnmanagedMemory;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.struct.CField;
import org.graalvm.nativeimage.c.struct.CStruct;
import org.graalvm.nativeimage.c.struct.SizeOf;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.word.PointerBase;
import org.graalvm.word.UnsignedWord;
import org.graalvm.word.WordFactory;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@CContext(BasicDirective.class)
public final class Basic {
    private Basic() {
        super();
    }

    public static void main(final String[] args) {
    }

    @CFunction("allocate_cstring")
    private static native CCharPointer allocateString(final UnsignedWord length);

    public static CCharPointer toCString(final CharSequence string) {
        final UnsignedWord length = CTypeConversion.toCString(string, StandardCharsets.UTF_8, WordFactory.nullPointer(), WordFactory.zero()).add(1);
        final CCharPointer pointer = Basic.allocateString(length);
        CTypeConversion.toCString(string, StandardCharsets.UTF_8, pointer, length);
        return pointer;
    }

    public static String fromCString(final CCharPointer ptr) {
        return CTypeConversion.utf8ToJavaString(ptr);
    }


    @CStruct("CTime")
    public interface Time extends PointerBase {
        @CField long second();
        @CField int nanos();
        @CField boolean special();

        @CField void second(long second);
        @CField void nanos(int nanos);
        @CField void special(boolean special);
    }

    public static Time toCTime(final Instant instant) {
        final Time time = UnmanagedMemory.malloc(SizeOf.get(Time.class));
        if (instant == Instant.MAX) {
            time.special(true);
            time.second(0);
            time.nanos(1);
            return time;
        }
        if (instant == Instant.MIN) {
            time.special(true);
            time.second(0);
            time.nanos(0);
            return time;
        }
        time.special(false);
        time.second(instant.getEpochSecond());
        time.nanos(instant.getNano());
        return time;
    }

    public static Instant fromCTime(final Time time) {
        if (time.special()) {
            if (time.second() != 0)
                throw new IllegalArgumentException("Invalid C-Time: special but invalid second");
            return switch (time.nanos()) {
                case 1 -> Instant.MAX;
                case 0 -> Instant.MIN;
                default -> throw new IllegalArgumentException("Invalid C-Time: special but undefined nanos");
            };
        }
        return Instant.ofEpochSecond(time.second(), time.nanos());
    }
}
