package com.wangdan.dream.persistence.orm.table;

import com.wangdan.dream.persistence.orm.sql.MySqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntityTableManager {
    private static Logger logger = LoggerFactory.getLogger(EntityTableManager.class);
    private Connection connection;

    public EntityTableManager(Connection connection) {
        this.connection = connection;
    }

    public void createTable(Class<?> clazz) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(MySqlHelper.getCreate(clazz));
        } catch (SQLException e) {
        }
    }

    public boolean isTableExist(Class<?> clazz) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(MySqlHelper.isTableExist(clazz));
            ResultSet resultSet = statement.getResultSet();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
