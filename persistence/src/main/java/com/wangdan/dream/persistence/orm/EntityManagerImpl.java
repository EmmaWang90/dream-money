package com.wangdan.dream.persistence.orm;

import java.sql.Connection;

public class EntityManagerImpl implements EntityManager {
    private Connection connection;

    public EntityManagerImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void setConnection(Connection connection) {

    }

    public boolean save(Object entity) {
        return false;
    }
}
