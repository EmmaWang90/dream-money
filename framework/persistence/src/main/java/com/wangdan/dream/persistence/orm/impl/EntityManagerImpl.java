package com.wangdan.dream.persistence.orm.impl;

import com.wangdan.dream.commons.serviceProperties.BeanUtils;
import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.ServiceBase;
import com.wangdan.dream.persistence.orm.DataBaseType;
import com.wangdan.dream.persistence.orm.EntityManager;
import com.wangdan.dream.persistence.orm.EntityTableManager;
import com.wangdan.dream.persistence.orm.filter.Condition;
import com.wangdan.dream.persistence.orm.impl.connection.DataBaseServiceImpl;
import com.wangdan.dream.persistence.orm.impl.connection.DatabaseConnectionFactory;
import com.wangdan.dream.persistence.orm.sql.EntityField;
import com.wangdan.dream.persistence.orm.sql.EntityMetaData;
import com.wangdan.dream.persistence.orm.sql.EntityMetaDataHelper;
import com.wangdan.dream.persistence.orm.sql.SqlHelper;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@InjectService(accessClass = EntityTableManager.class, value = EntityTableManagerImpl.class)
public class EntityManagerImpl<T> extends ServiceBase implements EntityManager<T> {
    private DataBaseType dataBaseType = DataBaseType.POSTGRESQL;
    @Service
    private DatabaseConnectionFactory databaseConnectionFactory;
    @Service
    private EntityTableManager entityTableManager;

    public EntityManagerImpl() {
        super(null);
    }

    public EntityManagerImpl(ServiceBase parent) {
        super(parent);
    }

    @Override
    public int delete(Class aClass, Condition condition) {
        return 0;
    }

    @Override
    public void delete(T entity) {
        String sql = SqlHelper.getDelete(getDataBaseType(), entity);
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement();) {
            statement.execute(sql);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private Connection getConnection() {
        DataBaseServiceImpl dataBaseService = this.databaseConnectionFactory.getService(getDataBaseType());
        return dataBaseService.getConnection();
    }

    public DataBaseType getDataBaseType() {
        return dataBaseType;
    }

    public EntityTableManager getEntityTableManager() {
        return entityTableManager;
    }

    private int getTypes(Class fieldClazz) {
        switch (fieldClazz.getSimpleName()) {
            case "Integer":
                return Types.INTEGER;
            case "Double":
                return Types.DOUBLE;
            case "String":
                return Types.VARCHAR;
            case "Date":
                return Types.DATE;
            case "Long":
                return Types.BIGINT;
        }
        return 0;
    }

    @Override
    public void modify(T entity) {
        String sql = SqlHelper.getModify(getDataBaseType(), entity);
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List query(Class<T> entityClass, Condition condition) {
        if (entityClass == null)
            return null;
        if (!entityTableManager.exist(getDataBaseType(), entityClass))
            entityTableManager.createTable(getDataBaseType(), entityClass);

        List<T> result = new ArrayList<>();
        try {
            String sql = SqlHelper.getQuery(getDataBaseType(), entityClass, condition);
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            EntityMetaData<T> entityMetaData = EntityMetaDataHelper.getEntityMetaData(entityClass);
            Map<String, EntityField> entityFieldMap = entityMetaData.getEntityFieldMap();
            while (resultSet.next()) {
                T instance = entityClass.newInstance();
                for (EntityField entityField : entityFieldMap.values()) {
                    Object object = readFromResultSet(entityField, resultSet);
                    entityField.getField().set(instance, object);
                }
                result.add(instance);
            }
        } catch (Exception e) {
            logger.error("failed to query for " + entityClass, condition, e);
        }
        return result;
    }

    private Object readFromResultSet(EntityField entityField, ResultSet resultSet) throws SQLException {
        Object value = null;
        String fieldName = entityField.getFieldName();
        switch (entityField.getClazz().getSimpleName()) {
            case "Integer": {
                value = resultSet.getInt(fieldName);
                break;
            }
            case "Double": {
                value = resultSet.getDouble(fieldName);
                break;
            }
            case "String": {
                value = resultSet.getString(fieldName);
                break;
            }
            case "Date": {
                Date date = resultSet.getDate(fieldName);
                if (date != null)
                    value = new Date(date.getTime());
                else
                    value = null;
                break;
            }
            case "Long": {
                value = resultSet.getLong(fieldName);
                break;
            }
            default:
                if (Enum.class.isAssignableFrom(entityField.getClazz())) {
                    String rawValue = resultSet.getString(fieldName);
                    if (rawValue != null)
                        value = BeanUtils.invoke(entityField.getClazz(), "parseName", rawValue);
                } else
                    throw new IllegalArgumentException("failed to process " + entityField.getClazz().getSimpleName());
        }
        return value;
    }

    public <T> boolean save(T... entityArray) {
        if (entityArray == null || entityArray.length == 0)
            return false;
        Class<T> entityClass = (Class<T>) entityArray[0].getClass();
        if (!entityTableManager.exist(getDataBaseType(), entityClass))
            entityTableManager.createTable(getDataBaseType(), entityClass);
        String sql = SqlHelper.getSave(getDataBaseType(), entityClass);
        DataBaseServiceImpl dataBaseService = this.databaseConnectionFactory.getService(getDataBaseType());
        Connection connection = dataBaseService.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            Map<String, EntityField> entityFieldMap = EntityMetaDataHelper.getEntityMetaData(entityClass).getEntityFieldMap();
            List<String> columnNameList = EntityMetaDataHelper.getEntityMetaData(entityClass).getColumnNameList();
            for (T entity : entityArray) {
                for (int i = 0; i < columnNameList.size(); i++) {
                    EntityField entityField = entityFieldMap.get(columnNameList.get(i));
                    Object fieldValue = BeanUtils.getField(entity, entityField.getColumnName());
                    setPreparedStatementParameter(entityField, fieldValue, preparedStatement, i);
                }
                logger.info("" + entity);
                preparedStatement.execute();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return true;
    }

    private void setPreparedStatementParameter(EntityField entityField, Object fieldValue, PreparedStatement preparedStatement, int i) throws SQLException {
        if (fieldValue == null) {
            preparedStatement.setNull(i + 1, getTypes(entityField.getClazz()));
            return;
        }
        switch (entityField.getClazz().getSimpleName()) {
            case "Integer": {
                Integer integer = (Integer) fieldValue;
                preparedStatement.setInt(i + 1, integer.intValue());
                break;
            }
            case "Double": {
                Double aDouble = (Double) fieldValue;
                preparedStatement.setDouble(i + 1, aDouble.doubleValue());
                break;
            }
            case "String": {
                String string = (String) fieldValue;
                preparedStatement.setString(i + 1, string);
                break;
            }
            case "Date": {
                java.util.Date date = (java.util.Date) fieldValue;
                Date sqlDate = new Date(date.getTime());
                preparedStatement.setDate(i + 1, sqlDate);
                break;
            }
            case "Long": {
                Long aLong = (Long) fieldValue;
                preparedStatement.setLong(i + 1, aLong);
                break;
            }
            default:
                if (Enum.class.isAssignableFrom(entityField.getClazz())) {
                    preparedStatement.setString(i + 1, (String) BeanUtils.invoke(fieldValue, "name"));
                } else
                    throw new IllegalArgumentException("failed to process " + entityField.getClazz().getSimpleName());

        }
    }

    @Inject
    public void setDatabaseConnectionFactory(DatabaseConnectionFactory databaseConnectionFactory) {
        this.addService(DatabaseConnectionFactory.class, databaseConnectionFactory);
    }
}
