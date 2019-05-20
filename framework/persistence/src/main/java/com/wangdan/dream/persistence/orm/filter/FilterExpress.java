package com.wangdan.dream.persistence.orm.filter;

import java.lang.reflect.Field;

public class FilterExpress {
    private String fieldName;
    private FilterType filterType;
    private Object validValue;

    public boolean isValid(Object instance) {
        Class clazz = instance.getClass();
        try {
            Field field = clazz.getField(fieldName);
            field.setAccessible(true);
            Object fieldValue = field.get(instance);
            return filterType.check(fieldValue, validValue);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String toSql() {
        StringBuilder stringBuilder = new StringBuilder("\"");
        stringBuilder.append(fieldName);
        stringBuilder.append("\"");
        stringBuilder.append(filterType.values());
        stringBuilder.append("\"");
        stringBuilder.append(validValue);
        if (filterType == FilterType.LIKE)
            stringBuilder.append("%");
        stringBuilder.append("\" ");
        return stringBuilder.toString();
    }
}
