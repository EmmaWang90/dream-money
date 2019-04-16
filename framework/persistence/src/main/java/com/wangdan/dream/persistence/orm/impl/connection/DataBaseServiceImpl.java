package com.wangdan.dream.persistence.orm.impl.connection;

import com.wangdan.dream.persistence.orm.DataBaseType;
import com.wangdan.dream.persistence.orm.sql.EntityField;
import com.wangdan.dream.persistence.orm.sql.EntityMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseServiceImpl.class);

    public Connection getConnection() {
        return connection;
    }

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

    public String execute(String sql) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    public <T> List<T> query(EntityMetaData<T> entityMetaData, String sql) {
        List<T> resultList = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            ResultSet resultSet = statement.getResultSet();
            List<EntityField> entityFieldList = entityMetaData.getEntityFieldList();
            while (resultSet.next()) {
                T instance = entityMetaData.newInstance();
                for (EntityField entityField : entityFieldList) {
                    String columnContent = resultSet.getString(entityField.getFieldName());
                    entityField.setInstanceFieldValue(instance, columnContent);
                }
                resultList.add(instance);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return resultList;
    }


}
