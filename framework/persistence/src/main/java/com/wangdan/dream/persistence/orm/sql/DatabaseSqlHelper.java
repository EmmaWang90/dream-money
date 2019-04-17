package com.wangdan.dream.persistence.orm.sql;

public interface DatabaseSqlHelper {
    String getCreateIndex(Class<?> clazz);

    String getCreateTable(Class<?> clazz);
}
