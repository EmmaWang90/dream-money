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

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        entityTableManager.dropTable(DataBaseType.POSTGRESQL, Person.class);
        boolean isExist = entityTableManager.exist(DataBaseType.POSTGRESQL, Person.class);
        assertFalse(isExist);
        entityTableManager.createTable(DataBaseType.POSTGRESQL, Person.class);
        isExist = entityTableManager.exist(DataBaseType.POSTGRESQL, Person.class);
        assertTrue(isExist);
        entityTableManager.dropTable(DataBaseType.POSTGRESQL, Person.class);
        isExist = entityTableManager.exist(DataBaseType.POSTGRESQL, Person.class);
        assertFalse(isExist);
    }
}
