package com.wangdan.dream.money.money;

import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.test.ServiceTestBase;
import com.wangdan.dream.money.Person;
import com.wangdan.dream.persistence.orm.DataBaseType;
import com.wangdan.dream.persistence.orm.impl.EntityTableManagerImpl;
import com.wangdan.dream.persistence.orm.impl.connection.DatabaseConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@InjectService(DatabaseConnectionFactory.class)
@InjectService(EntityTableManagerImpl.class)
public class TestTableManager extends ServiceTestBase {
    @Service
    private DatabaseConnectionFactory databaseConnectionFactory = null;
    @Service
    private EntityTableManagerImpl entityTableManager;

    public TestTableManager() {
        super(null);
    }

    @BeforeEach
    @Override
    public void start() {
        super.start();
    }

    @Test
    public void testCreateTable() {
        entityTableManager.createTable(DataBaseType.POSTGRESQL, Person.class);
    }
}
