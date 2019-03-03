package com.wangdan.dream.persistence.orm.impl;

import com.wangdan.dream.persistence.orm.EntityManager;
import com.wangdan.dream.persistence.orm.EntityTableManager;

public class EntityManagerImpl implements EntityManager {
    private EntityTableManager entityTableManager;

    public EntityTableManager getEntityTableManager() {
        return entityTableManager;
    }

    public boolean save(Object entity) {
        return false;
    }
}
