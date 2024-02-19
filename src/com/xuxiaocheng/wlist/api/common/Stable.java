package com.xuxiaocheng.wlist.api.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the API is stable.
 * When this annotation is applied to not field nor method,
 * it means that all public fields and methods inside it are stable.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.PACKAGE, ElementType.MODULE})
public @interface Stable {
    /**
     * The start version in which the annotated method became stable.
     * @return the version string.
     */
    String since();

    /**
     * The current module corresponding to {@code since} version string.
     * @return the corresponding module.
     */
    StableModule module() default StableModule.Core;
}
