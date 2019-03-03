package com.wangdan.dream.persistence.orm.impl.connection;

import com.wangdan.dream.persistence.orm.DataBaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseServiceImpl.class);
    private Connection connection;
    private DataBaseType dataBaseType;

    public DataBaseServiceImpl(DataBaseType dataBaseType, Connection connection) {
        this.dataBaseType = dataBaseType;
        this.connection = connection;
    }


    public void commit(String sql) {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

    }
}
