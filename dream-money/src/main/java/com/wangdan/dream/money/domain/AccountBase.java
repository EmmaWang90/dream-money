package com.wangdan.dream.money.domain;

import com.wangdan.dream.money.orm.Column;
import com.wangdan.dream.money.orm.Table;

@Table
public class AccountBase {
    @Column
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer total;
}
