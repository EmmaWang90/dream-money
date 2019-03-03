package com.wangdan.dream.framework;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(InjectServices.class)
public @interface InjectService {
    Class<? extends ServiceBase> value() default ServiceBase.class;
    Class<? extends ServiceBase> accessClass() default ServiceBase.class;

    Class<? extends ServiceBase> implementation() default ServiceBase.class;
}
