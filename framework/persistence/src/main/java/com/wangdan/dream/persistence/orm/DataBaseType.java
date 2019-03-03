package com.wangdan.dream.persistence.orm;

public enum DataBaseType {
    POSTGRESQL("pgsql");
    private String value;

    private DataBaseType(String value) {
        this.value = value;
    }

    public static DataBaseType parse(String value) {
        for (DataBaseType temp : DataBaseType.values())
            if (temp.value.equals(value))
                return temp;
        return null;
    }

    public String value() {
        return value;
    }
}
