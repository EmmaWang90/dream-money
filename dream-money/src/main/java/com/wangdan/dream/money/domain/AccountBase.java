package com.wangdan.dream.money.domain;


import com.wangdan.dream.persistence.orm.annotations.Column;
import com.wangdan.dream.persistence.orm.annotations.Table;

@Table
public class AccountBase {
    @Column
    private Integer id;
    @Column
    private String name;
}
