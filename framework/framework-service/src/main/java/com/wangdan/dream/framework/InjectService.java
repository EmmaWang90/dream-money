package com.wangdan.dream.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(InjectServices.class)
public @interface InjectService {
    Class<?> accessClass() default ServiceBase.class;

    Class<? extends ServiceBase> implementation() default ServiceBase.class;

    Class<? extends ServiceBase> value() default ServiceBase.class;
}
