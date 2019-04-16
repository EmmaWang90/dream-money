package com.wangdan.dream.commons.serviceProperties;

import java.lang.reflect.Field;

public class BeanUtils {
    public static Object getField(Object instance, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void setFiled(Object instance, String fieldName, Object filedValue) throws NoSuchFieldException, IllegalAccessException {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, filedValue);
        } catch (Exception e) {
            throw e;
        }
    }
}
