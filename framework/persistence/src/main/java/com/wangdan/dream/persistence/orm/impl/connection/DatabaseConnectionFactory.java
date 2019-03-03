package com.wangdan.dream.persistence.orm.impl.connection;

import com.wangdan.dream.commons.serviceProperties.PropertyFile;
import com.wangdan.dream.framework.ServiceBase;
import com.wangdan.dream.framework.ServiceFactoryBase;
import com.wangdan.dream.persistence.orm.DataBaseType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@PropertyFile("com.wangdan.dream.database.cfg")
public class DatabaseConnectionFactory extends ServiceFactoryBase {
    public DatabaseConnectionFactory(ServiceBase parent) {
        super(parent);
    }

    @Override
    protected Object createService(String serviceName, Properties properties) {
        String dbUrl = properties.getProperty("url");
        String username = properties.getProperty("user");
        String password = properties.getProperty("password");
        try {
            Connection connection = DriverManager.getConnection(dbUrl, username, password);
            return new DataBaseServiceImpl(DataBaseType.parse(serviceName), connection);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public DataBaseServiceImpl getService(DataBaseType dataBaseType) {
        return (DataBaseServiceImpl) getService(dataBaseType.value());
    }
}
