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
import com.wangdan.dream.persistence.orm.sql.EntityMetaDataHelper;
import com.wangdan.dream.persistence.orm.sql.SqlHelper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@InjectService(accessClass = EntityTableManager.class, value = EntityTableManagerImpl.class)
public class EntityManagerImpl<T> extends ServiceBase implements EntityManager<T> {
    private DataBaseType dataBaseType = DataBaseType.POSTGRESQL;
    @Service
    private DatabaseConnectionFactory databaseConnectionFactory;
    @Service
    private EntityTableManager entityTableManager;

    public EntityManagerImpl(ServiceBase parent) {
        super(parent);
    }

    @Override
    public int delete(Class aClass, Condition condition) {
        return 0;
    }

    public EntityTableManager getEntityTableManager() {
        return entityTableManager;
    }

    public DataBaseType getDataBaseType() {
        return dataBaseType;
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
    public List query(Class aClass, Condition condition) {
        return null;
    }

    public <T> boolean save(T... entityArray) {
        if (entityArray == null || entityArray.length == 0)
            return false;
        Class<T> entityClass = (Class<T>) entityArray[0].getClass();
        if (!entityTableManager.exist(getDataBaseType(), entityClass))
            entityTableManager.createTable(getDataBaseType(), entityClass);
        String sql = SqlHelper.getSave(entityClass);
        DataBaseServiceImpl dataBaseService = this.databaseConnectionFactory.getService(getDataBaseType());
        Connection connection = dataBaseService.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (T entity : entityArray) {
                List<EntityField> entityFieldList = EntityMetaDataHelper.getEntityMetaData(entityClass).getEntityFieldList();
                for (int i = 0; i < entityFieldList.size(); i++) {
                    EntityField entityField = entityFieldList.get(i);
                    Object fieldValue = BeanUtils.getField(entity, entityField.getFieldName());
                    setPreparedStatementParameter(entityField, fieldValue, preparedStatement, i);
                }
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
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
                Date date = (Date) fieldValue;

                preparedStatement.setDate(i + 1, date);
                break;
            }
            case "Long": {
                Long aLong = (Long) fieldValue;
                preparedStatement.setLong(i + 1, aLong);
                break;
            }
            default:
                throw new IllegalArgumentException("failed to process " + entityField.getClazz().getSimpleName());

        }
    }
}
