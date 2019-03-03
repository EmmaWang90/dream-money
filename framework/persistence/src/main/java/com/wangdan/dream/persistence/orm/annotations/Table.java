package com.wangdan.dream.persistence.orm.annotations;

import com.wangdan.dream.persistence.orm.table.TableName;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String value() default "";

    Class<? extends TableName> tableClass() default TableName.class;

    String period() default "YEAR";
}
