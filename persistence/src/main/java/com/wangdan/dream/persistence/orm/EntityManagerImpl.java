package com.wangdan.dream.persistence.orm;

import com.wangdan.dream.persistence.orm.sql.EntityTable;
import com.wangdan.dream.persistence.orm.table.EntityTableManager;

import java.sql.Connection;

public class EntityManagerImpl implements EntityManager {
    private Connection connection;

    public EntityTableManager getEntityTableManager() {
        return entityTableManager;
    }

    private EntityTableManager entityTableManager;

    public EntityManagerImpl(Connection connection) {
        this.connection = connection;
        entityTableManager = new EntityTableManager(connection);
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean save(Object entity) {
        return false;
    }
}
