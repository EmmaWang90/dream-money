package com.wangdan.dream.money.domain;

import com.wangdan.dream.persistence.orm.annotations.Column;
import com.wangdan.dream.persistence.orm.annotations.Table;

import java.util.Date;

@Table
public class Record {
    @Column
    private Long id;
    @Column
    private Date time;
    @Column
    private RecordType recordType;
    @Column
    private String category;
    @Column
    private String subclass;
    @Column
    private String comment;

}
