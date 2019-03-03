package com.wangdan.dream.persistence.orm.sql;


import java.util.function.BiFunction;

public class EntityTable {
    private BiFunction<String, String, String> getNameFunction;
    private String period;
    private String tableName;

    public EntityTable(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        if (getNameFunction == null)
            return tableName;
        else
            return getNameFunction.apply(period, tableName);
    }

    public void setGetNameFunction(BiFunction<String, String, String> getNameFunction) {
        this.getNameFunction = getNameFunction;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
