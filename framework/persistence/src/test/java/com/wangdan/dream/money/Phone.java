package com.wangdan.dream.money;

import com.wangdan.dream.persistence.orm.annotations.Column;
import com.wangdan.dream.persistence.orm.annotations.Table;

@Table
public class Phone {
    @Column
    private String color;
}
