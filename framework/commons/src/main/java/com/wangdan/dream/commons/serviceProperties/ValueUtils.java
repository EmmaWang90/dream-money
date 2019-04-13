package com.wangdan.dream.commons.serviceProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

public class ValueUtils {
    public static final Logger logger = LoggerFactory.getLogger(ValueUtils.class);

    private static Object convertPrimitiveValue(String className, String content) {
        switch (className) {
            case "int":
                return Integer.valueOf(content);
            case "double":
                return Double.valueOf(content);
            case "short":
                return Short.valueOf(content);
            case "byte":
            case "char":
                return Byte.valueOf(content);
            case "long":
                return Long.valueOf(content);
        }
        throw new IllegalArgumentException("className is " + className);
    }

    public static Object convertValue(Class<?> tClass, String content) {
        if (tClass == null || content == null || content.trim().isEmpty())
            throw new IllegalArgumentException("convertValue with illegalInput" + tClass + ", " + content);
        String className = tClass.getSimpleName();
        if (tClass.isPrimitive())
            return convertPrimitiveValue(className, content);
        else if (tClass.isArray()) {
            throw new IllegalArgumentException("convertValue with illegalInput" + tClass + ", " + content);
        } else if (tClass.isEnum()) {
            Class<Enum> enumClass = (Class<Enum>) tClass;
            return Enum.valueOf(enumClass, content);
        } else if (tClass.isMemberClass()) {
            try {
                Constructor stringConstructor = tClass.getConstructor(String.class);
                return stringConstructor.newInstance(content);
            } catch (NoSuchMethodException e) {
                logger.error("try to convertValue from {} to {}, failed to find constructor with parameter String.class", content, tClass);
            } catch (Exception e) {
                logger.error("error happend when  convertValue from {} to {} using constructor with parameter String.class", content, tClass);
            }
            return tClass.cast(content);
        } else
            throw new IllegalArgumentException("convertValue with illegalInput" + tClass + ", " + content);
    }
}
