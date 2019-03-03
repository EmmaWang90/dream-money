package com.wangdan.dream.persistence.orm.connection;

import com.wangdan.dream.commons.serviceProperties.PropertyFile;
import com.wangdan.dream.framework.ServiceBase;
import com.wangdan.dream.framework.ServiceFactoryBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@PropertyFile("com.wangdan.dream.database.cfg")
public class DatabaseConnectionFactory extends ServiceFactoryBase {
    private Connection connection;

    public DatabaseConnectionFactory(ServiceBase parent) {
        super(parent);
    }

    @Override
    protected Object createService(String serviceName, Properties properties) {
        String dbUrl = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        try {
            Connection connection = DriverManager.getConnection(dbUrl, username, password);
            return connection;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
