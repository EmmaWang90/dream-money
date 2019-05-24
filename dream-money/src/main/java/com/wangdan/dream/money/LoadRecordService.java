package com.wangdan.dream.money;

import com.wangdan.dream.money.domain.Record;

import java.util.List;

public interface LoadRecordService {
    List<Record> load(String fileName);

}
