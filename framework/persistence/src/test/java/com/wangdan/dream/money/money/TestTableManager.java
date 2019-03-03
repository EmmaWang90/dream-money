package com.wangdan.dream.money.money;

import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.test.ServiceTestBase;
import com.wangdan.dream.money.Person;
import com.wangdan.dream.persistence.orm.DatabaseServiceImpl;
import com.wangdan.dream.persistence.orm.EntityManager;
import com.wangdan.dream.persistence.orm.table.EntityTableManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@InjectService(DatabaseServiceImpl.class)
public class TestTableManager extends ServiceTestBase {
    @Service
    private DatabaseServiceImpl databaseService = null;
    private EntityManager entityManager = null;

    public TestTableManager() {
        super(null);
    }

    @BeforeEach
    @Override
    public void start() {
        super.start();
        entityManager = databaseService.getEntityManager();
    }

    @Test
    public void testCreateTable() {
        EntityTableManager entityTableManager = entityManager.getEntityTableManager();
        entityTableManager.createTable(Person.class);
        assertTrue(entityTableManager.isTableExist(Person.class));
    }
}
