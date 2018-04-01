package com.wangdan.dream.persistence.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String value() default "";

    boolean isPrimaryKey() default false;

    int displaySize() default 15;

    boolean notNull() default false;

    boolean unsigned() default false;
}
