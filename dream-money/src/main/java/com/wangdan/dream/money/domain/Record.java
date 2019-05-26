package com.wangdan.dream.money.domain;

import com.wangdan.dream.commons.serviceProperties.document.name;
import com.wangdan.dream.persistence.orm.annotations.Column;
import com.wangdan.dream.persistence.orm.annotations.Table;

import java.util.Date;

@Table
public class Record {
    @Column
    @name("账户")
    private String account;
    @Column
    @name("分类")
    private String category;
    @Column
    private String comment;
    @Column
    @name("内容")
    private String content;
    @Column
    @name("货币")
    private Currency currency;
    @Column
    @name("子内容")
    private String detail;
    @Column
    private Long id;
    @Column
    @name("金额")
    private Double money;
    @Column
    @name("收入/支出")
    private RecordType recordType;
    @Column
    @name("子分类")
    private String subCategory;
    @Column
    @name("期间")
    private Date time;


}
