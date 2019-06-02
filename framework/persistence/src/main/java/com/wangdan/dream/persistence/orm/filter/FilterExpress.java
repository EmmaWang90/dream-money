package com.wangdan.dream.persistence.orm.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;

@Getter
@Setter
@ToString
public class FilterExpress {
    private String fieldName;
    private FilterType filterType;
    private Object validValue;

    public FilterExpress(String fieldName, FilterType filterType, Object validValue) {
        this.fieldName = fieldName;
        this.filterType = filterType;
        this.validValue = validValue;
    }

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

    public String toSql(Class entityClass) throws NoSuchFieldException {
        Class fieldClass = entityClass.getDeclaredField(fieldName).getType();
        StringBuilder stringBuilder = new StringBuilder("\"");
        stringBuilder.append(fieldName.toLowerCase());
        stringBuilder.append("\"");
        stringBuilder.append(filterType.value());
        if (fieldClass == String.class || Enum.class.isAssignableFrom(fieldClass))
            stringBuilder.append("\'");
        stringBuilder.append(validValue);
        if (filterType == FilterType.LIKE)
            stringBuilder.append("%");
        if (fieldClass == String.class || Enum.class.isAssignableFrom(fieldClass))
            stringBuilder.append("\' ");
        return stringBuilder.toString();
    }
}
