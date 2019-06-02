package com.wangdan.dream.money.domain;

import java.util.Arrays;
import java.util.List;

public enum RecordType {
    EXPENSE("支出"), INCOME("收入"), TRANSFER_INTO("转入"), TRANSFER_OUT("转账到"), CHANGE_REMAIN("收入余额", "支出余额");
    private List<String> values;

    private RecordType(String... values) {
        this.values = Arrays.asList(values);
    }

    public static RecordType parse(String value) {
        for (RecordType recordType : RecordType.values()) {
            if (recordType.values.contains(value))
                return recordType;
        }
        throw new IllegalArgumentException();
    }
}
