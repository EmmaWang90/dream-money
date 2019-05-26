package com.wangdan.dream.money.record;


import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.test.ServiceTestBase;
import com.wangdan.dream.money.LoadRecordService;
import com.wangdan.dream.money.domain.Record;
import com.wangdan.dream.money.load.LoadRecordServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@InjectService(accessClass = LoadRecordService.class, implementation = LoadRecordServiceImpl.class)
public class TestLoad extends ServiceTestBase {
    @Service
    private LoadRecordService loadRecordService;
    @Test
    public void testLoad() {
        List<Record> recordList = loadRecordService.load("./src/test/resources/2019-01-01 _ 12-31.xls");
        assertEquals(recordList.size(), 536);
    }
}
