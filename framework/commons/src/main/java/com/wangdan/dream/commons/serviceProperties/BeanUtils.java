package com.wangdan.dream.commons.serviceProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static java.util.stream.Collectors.toSet;

public class BeanUtils {
    public static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
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

    public static Object invoke(Class<?> instance, String methodName, Object... arguments) {
        Class<?>[] argumentsTypes = (Class<?>[]) Arrays.stream(arguments).map(Object::getClass).collect(toSet()).toArray(new Class<?>[]{});
        try {
            Method method = instance.getMethod(methodName, argumentsTypes);
            return method.invoke(instance, arguments);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
