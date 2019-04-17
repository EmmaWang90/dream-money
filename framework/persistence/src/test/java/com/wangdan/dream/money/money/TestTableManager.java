package com.wangdan.dream.money.money;

import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.test.ServiceTestBase;
import com.wangdan.dream.money.Person;
import com.wangdan.dream.money.Phone;
import com.wangdan.dream.persistence.orm.DataBaseType;
import com.wangdan.dream.persistence.orm.EntityTableManager;
import com.wangdan.dream.persistence.orm.impl.EntityTableManagerImpl;
import com.wangdan.dream.persistence.orm.impl.connection.DatabaseConnectionFactory;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@InjectService(DatabaseConnectionFactory.class)
@InjectService(accessClass = EntityTableManager.class, value = EntityTableManagerImpl.class)
public class TestTableManager extends ServiceTestBase {
    @Service
    private DatabaseConnectionFactory databaseConnectionFactory = null;
    @Service
    private EntityTableManager entityTableManager;

    @Test
    public void testCreateTable() {
        testTableManager(Person.class);
        testTableManager(Phone.class);

    }

    private void testTableManager(Class<?> entityClass) {
        entityTableManager.dropTable(DataBaseType.POSTGRESQL, entityClass);
        boolean isExist = entityTableManager.exist(DataBaseType.POSTGRESQL, entityClass);
        assertFalse(isExist);
        entityTableManager.createTable(DataBaseType.POSTGRESQL, entityClass);
        isExist = entityTableManager.exist(DataBaseType.POSTGRESQL, entityClass);
        assertTrue(isExist);
        entityTableManager.clearTable(DataBaseType.POSTGRESQL, entityClass);


        entityTableManager.dropTable(DataBaseType.POSTGRESQL, entityClass);
        isExist = entityTableManager.exist(DataBaseType.POSTGRESQL, entityClass);
        assertFalse(isExist);
    }
}
