package com.wangdan.dream.persistence.orm.sql;

import com.wangdan.dream.persistence.orm.annotations.Column;

import java.lang.reflect.Field;
import java.util.Date;

public class EntityField {
    private Class<?> clazz;
    private Column column;
    private Field field;
    private String fieldName;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getCreateFieldString() {
        StringBuilder stringBuilder = new StringBuilder(getFieldString());
        if (column.isPrimaryKey())
            stringBuilder.append(" primary key");
        if (column.notNull())
            stringBuilder.append(" not null");
        if (column.unsigned())
            stringBuilder.append(" unsigned");
        return stringBuilder.toString();
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldParameter() {
        if (clazz.equals(Integer.class))
            return "int ";
        else if (clazz.equals(String.class) || clazz.equals(CharSequence.class))
            return "varchar(" + column.displaySize() + ")";
        else if (clazz.equals(Double.class) || clazz.equals(Float.class))
            return "decimal(" + (column.displaySize() - 3) + ", 3)";
        else if (clazz.equals(Date.class))
            return "date";
        else
            throw new IllegalArgumentException("out of capabillity");
    }

    public String getFieldString() {
        return fieldName + " " + getFieldParameter();
    }
}