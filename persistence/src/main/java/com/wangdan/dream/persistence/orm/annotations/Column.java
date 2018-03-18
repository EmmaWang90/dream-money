package com.wangdan.dream.persistence.orm.annotations;

import com.wangdan.dream.persistence.orm.table.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String value() default "";

    boolean isPrimaryKey() default false;

    DataType dataType() default DataType.STRING;
}
