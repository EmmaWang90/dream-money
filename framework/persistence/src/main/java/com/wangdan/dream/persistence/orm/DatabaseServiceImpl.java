package com.wangdan.dream.persistence.orm;

import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.ServiceBase;
import com.wangdan.dream.persistence.orm.connection.DatabaseConnectionFactory;

@InjectService(accessClass = DatabaseConnectionFactory.class, implementation = DatabaseConnectionFactory.class)
public class DatabaseServiceImpl extends ServiceBase {
    @Service
    private DatabaseConnectionFactory databaseConnectionFactory;
    private EntityManager entityManager;

    public DatabaseServiceImpl(ServiceBase parent) {
        super(parent);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void start() {
        super.start();
        entityManager = new EntityManagerImpl(databaseConnectionFactory.getConnection());
    }
}
