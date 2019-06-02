package com.wangdan.dream.money.record;


import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.test.ServiceTestBase;
import com.wangdan.dream.money.LoadRecordService;
import com.wangdan.dream.money.domain.Record;
import com.wangdan.dream.money.load.LoadRecordServiceImpl;
import com.wangdan.dream.persistence.orm.DataBaseType;
import com.wangdan.dream.persistence.orm.EntityManager;
import com.wangdan.dream.persistence.orm.impl.EntityManagerImpl;
import com.wangdan.dream.persistence.orm.impl.connection.DatabaseConnectionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@InjectService(accessClass = LoadRecordService.class, implementation = LoadRecordServiceImpl.class)
@InjectService(DatabaseConnectionFactory.class)
@InjectService(accessClass = EntityManager.class, value = EntityManagerImpl.class)
public class TestLoad extends ServiceTestBase {
    @Service
    private LoadRecordService loadRecordService;
    @Service
    private EntityManager entityManager;
    @Test
    public void testLoad() {
        entityManager.getEntityTableManager().dropTable(DataBaseType.POSTGRESQL, Record.class);
        List<Record> recordList = loadRecordService.load("./src/test/resources/2019-01-01 _ 12-31.xls");
        assertEquals(recordList.size(), 535);
        entityManager.save(recordList.toArray());
        entityManager.getEntityTableManager().dropTable(DataBaseType.POSTGRESQL, Record.class);
    }
}
