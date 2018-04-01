package com.wangdan.dream.persistence.orm.connection;

import com.wangdan.dream.commons.serviceProperties.Property;
import com.wangdan.dream.commons.serviceProperties.PropertyFile;
import com.wangdan.dream.framework.ServiceBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@PropertyFile("com.wangdan.dream.database.cfg")
public class DatabaseConnectionFactory extends ServiceBase {
    private Connection connection;
    @Property("mysql.url")
    private String dbUrl;
    @Property("mysql.driver")
    private String driverClass;
    @Property("mysql.password")
    private String password;
    @Property("mysql.user")
    private String user;

    public DatabaseConnectionFactory(ServiceBase parent) {
        super(parent);
    }

    @Override
    public void start() {
        super.start();
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(dbUrl, user, password);
                connection.setSchema("test");
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                logger.error("failed to connect db", e);
            }
        }
        return connection;
    }
}
