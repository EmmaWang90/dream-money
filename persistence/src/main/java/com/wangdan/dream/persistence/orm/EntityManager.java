package com.wangdan.dream.persistence.orm;

import com.wangdan.dream.persistence.orm.table.EntityTableManager;

import java.sql.Connection;

public interface EntityManager<T> {

    void setConnection(Connection connection);

    boolean save(T entity);

    EntityTableManager getEntityTableManager();
}
