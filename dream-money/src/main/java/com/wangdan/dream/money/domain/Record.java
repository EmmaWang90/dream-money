package com.wangdan.dream.money.domain;

import com.wangdan.dream.commons.serviceProperties.document.name;
import com.wangdan.dream.persistence.orm.annotations.Column;
import com.wangdan.dream.persistence.orm.annotations.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Record {
    @Column
    @name("账户")
    private String account;
    @Column(displaySize = 256)
    @name("分类")
    private String category;
    @Column
    private String comment;
    @Column(displaySize = 512)
    @name("内容")
    private String content;
    @Column
    @name("货币")
    private Currency currency;
    @Column(displaySize = 512)
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
    @name("子类别")
    private String subCategory;
    @Column
    @name("期间")
    private Date time;


}
