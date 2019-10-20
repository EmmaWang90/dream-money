package com.wangdan.dream.money.record;


import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.test.ServiceTestBase;
import com.wangdan.dream.money.LoadRecordService;
import com.wangdan.dream.money.domain.Record;
import com.wangdan.dream.money.domain.RecordType;
import com.wangdan.dream.money.load.LoadRecordServiceImpl;
import com.wangdan.dream.persistence.orm.DataBaseType;
import com.wangdan.dream.persistence.orm.EntityManager;
import com.wangdan.dream.persistence.orm.filter.Condition;
import com.wangdan.dream.persistence.orm.filter.FilterExpress;
import com.wangdan.dream.persistence.orm.filter.FilterGroup;
import com.wangdan.dream.persistence.orm.filter.FilterType;
import com.wangdan.dream.persistence.orm.impl.EntityManagerImpl;
import com.wangdan.dream.persistence.orm.impl.connection.DatabaseConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@InjectService(accessClass = LoadRecordService.class, implementation = LoadRecordServiceImpl.class)
@InjectService(DatabaseConnectionFactory.class)
@InjectService(accessClass = EntityManager.class, value = EntityManagerImpl.class)
public class TestLoad extends ServiceTestBase {
    @Service
    private LoadRecordService loadRecordService;
    @Service
    private EntityManager entityManager;

    @Before
    @After
    public void cleanDatabase() {
        entityManager.getEntityTableManager().dropTable(DataBaseType.POSTGRESQL, Record.class);
    }
    @Test
    public void testLoad() {
        List<Record> recordList = loadRecordService.load("./src/test/resources/2019-01-01 _ 12-31.xls");
        assertEquals(recordList.size(), 535);
        entityManager.save(recordList.toArray());

        FilterExpress filterExpress = new FilterExpress("recordType", FilterType.EQUAL, RecordType.INCOME.name());
        FilterGroup filterGroup = new FilterGroup(Arrays.<FilterExpress>asList(filterExpress));
        Condition condition = new Condition();
        condition.setFilterGroup(filterGroup);
        List<Record> incomeRecordList = entityManager.query(Record.class, condition);
        assertNotEquals(incomeRecordList.size(), 0);
    }
}
