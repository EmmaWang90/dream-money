package com.wangdan.dream.persistence.orm.filter;

import java.lang.reflect.Field;
import java.util.List;

public class FilterExpress {
    private String fieldName;
    private FilterType filterType;
    private List<Object> validValueList;

    public boolean isValid(Object instance) {
        Class clazz = instance.getClass();
        try {
            Field field = clazz.getField(fieldName);
            field.setAccessible(true);
            Object fieldValue = field.get(instance);
            return filterType.check(fieldValue, validValueList);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
