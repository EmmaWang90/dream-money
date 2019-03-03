package com.wangdan.dream.persistence.orm;

import com.wangdan.dream.persistence.orm.table.EntityTableManager;

import java.sql.Connection;

public interface EntityManager<T> {

    EntityTableManager getEntityTableManager();

    boolean save(T entity);

    void setConnection(Connection connection);
}
