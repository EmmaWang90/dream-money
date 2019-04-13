package com.wangdan.dream.commons.serviceProperties;

import java.lang.reflect.Field;

public class BeanUtils {
    public static void setFiled(Object instance, String fieldName, Object filedValue) throws NoSuchFieldException, IllegalAccessException {
        try {
            Field field = instance.getClass().getField(fieldName);
            field.set(instance, filedValue);
        } catch (Exception e) {
            throw e;
        }
    }
}
