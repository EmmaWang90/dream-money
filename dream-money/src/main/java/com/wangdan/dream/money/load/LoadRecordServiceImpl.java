package com.wangdan.dream.money.load;

import com.wangdan.dream.framework.ServiceBase;
import com.wangdan.dream.money.LoadRecordService;
import com.wangdan.dream.money.domain.Record;

import java.util.List;

public class LoadRecordServiceImpl extends ServiceBase implements LoadRecordService {
    public LoadRecordServiceImpl(ServiceBase parent) {
        super(parent);
    }


    @Override
    public List<Record> load(String fileName) {

    }
}
