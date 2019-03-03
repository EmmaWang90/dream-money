package com.wangdan.dream.persistence.orm;

import com.wangdan.dream.persistence.orm.table.EntityTableManager;

import java.sql.Connection;

public class EntityManagerImpl implements EntityManager {
    private Connection connection;
    private EntityTableManager entityTableManager;

    public EntityManagerImpl(Connection connection) {
        this.connection = connection;
        entityTableManager = new EntityTableManager(connection);
    }

    public EntityTableManager getEntityTableManager() {
        return entityTableManager;
    }

    public boolean save(Object entity) {
        return false;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
