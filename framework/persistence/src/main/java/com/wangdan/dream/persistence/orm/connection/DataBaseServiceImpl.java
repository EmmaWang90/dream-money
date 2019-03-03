package com.wangdan.dream.persistence.orm.connection;

import java.sql.Connection;

public class DataBaseServiceImpl {
    private Connection connection;
    private DataBaseType dataBaseType;

    public DataBaseServiceImpl(DataBaseType dataBaseType, Connection connection) {
        this.dataBaseType = dataBaseType;
        this.connection = connection;
    }
}
