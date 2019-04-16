package com.wangdan.dream.persistence.orm;

public interface EntityTableManager {
    void clearTable(DataBaseType dataBaseType, Class<?> entityClass);

    void createTable(DataBaseType dataBaseType, Class<?> entityClass);

    void dropTable(DataBaseType dataBaseType, Class<?> entityClass);

    <T> boolean exist(DataBaseType dataBaseType, Class<T> entityClass);

}
