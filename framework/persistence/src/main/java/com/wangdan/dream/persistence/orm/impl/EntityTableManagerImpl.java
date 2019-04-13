package com.wangdan.dream.persistence.orm.impl;

import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.ServiceBase;
import com.wangdan.dream.persistence.orm.DataBaseType;
import com.wangdan.dream.persistence.orm.EntityTableManager;
import com.wangdan.dream.persistence.orm.impl.connection.DatabaseConnectionFactory;
import com.wangdan.dream.persistence.orm.sql.EntityMetaData;
import com.wangdan.dream.persistence.orm.sql.EntityMetaDataHelper;
import com.wangdan.dream.persistence.orm.sql.SqlHelper;

public class EntityTableManagerImpl extends ServiceBase implements EntityTableManager {
    @Service
    private DatabaseConnectionFactory databaseConnectionFactory;

    public EntityTableManagerImpl(ServiceBase parent) {
        super(parent);
    }

    @Override
    public void clearTable(DataBaseType dataBaseType, Class<?> entityClass) {

    }

    @Override
    public void createTable(DataBaseType dataBaseType, Class<?> entityClass) {
        String createSql = SqlHelper.getCreate(dataBaseType, entityClass);
        this.databaseConnectionFactory.getService(dataBaseType).commit(createSql);
    }

    @Override
    public void dropTable(DataBaseType dataBaseType, Class<?> entityClass) {
        String createSql = SqlHelper.getDrop(dataBaseType, entityClass);
        this.databaseConnectionFactory.getService(dataBaseType).commit(createSql);
    }

    @Override
    public <T> boolean exist(DataBaseType dataBaseType, Class<T> entityClass) {
        String createSql = SqlHelper.isTableExist(dataBaseType, entityClass);
        EntityMetaData<T> entityMetaData = EntityMetaDataHelper.getEntityMetaData(entityClass);
        String result = this.databaseConnectionFactory.getService(dataBaseType).execute(createSql);
        if (result != null && !result.trim().isEmpty())
            return true;
        else
            return false;
    }

}
