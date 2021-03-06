package com.wangdan.dream.persistence.orm.sql;

import com.google.common.base.Joiner;
import com.wangdan.dream.persistence.orm.DataBaseType;
import com.wangdan.dream.persistence.orm.filter.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlHelper {
    public static final Logger logger = LoggerFactory.getLogger(SqlHelper.class);
    public static final DataBaseType defaultDatabaseType = DataBaseType.POSTGRESQL;
    private static Map<DataBaseType, DatabaseSqlHelper> databaseSqlHelperMap = new HashMap<>();

    static {
        databaseSqlHelperMap.put(DataBaseType.POSTGRESQL, new PostgreHelper());
    }

    public static String[] getCreate(DataBaseType dataBaseType, Class<?> clazz) {
        String createTableSql = getCreate(clazz);
        String createIndexSql = databaseSqlHelperMap.get(dataBaseType).getCreateIndex(clazz);
        return new String[]{createTableSql, createIndexSql};
    }

    private static <T> String createColumnValueString(Collection<EntityField> entityFieldSet, T entity, String delimiter) {
        return entityFieldSet.stream().map(entityField -> {
            return createColumnValueString(entityField, entity);
        }).collect(Collectors.joining(delimiter));
    }

    private static <T> String createColumnValueString(EntityField entityField, T entity) {
        Field field = entityField.getField();
        field.setAccessible(true);
        try {
            Object fieldValue = field.get(entity);
            String string = "\"" + entityField.getFieldName() + "\"" + " = ";
            if (fieldValue instanceof String) {
                string += "\'" + fieldValue + "\'";
            } else
                string += fieldValue;
            return string;
        } catch (IllegalAccessException e) {
            logger.error("failed to create modify sql", e);
        }
        return null;
    }

    public static <T> String getDelete(DataBaseType dataBaseType, T entity) {
        StringBuilder stringBuilder = new StringBuilder("DELETE FROM ");
        EntityMetaData entityMetaData = EntityMetaDataHelper.getEntityMetaData(entity.getClass());
        stringBuilder.append(entityMetaData.getTableName());
        stringBuilder.append(" WHERE ");
        stringBuilder.append(createColumnValueString((Collection<EntityField>) entityMetaData.getPrimaryFieldMap().values(), entity, " AND "));
        return stringBuilder.toString();
    }

    public static <T> String getModify(DataBaseType dataBaseType, T entity) {
        Class<T> entityClass = (Class<T>) entity.getClass();
        EntityMetaData entityMetaData = EntityMetaDataHelper.getEntityMetaData(entityClass);
        StringBuilder stringBuilder = new StringBuilder("UPDATE ");
        stringBuilder.append(entityMetaData.getTableName());
        stringBuilder.append(" SET ");
        Map<String, EntityField> entityFieldMap = entityMetaData.getEntityFieldMap();
        Collection<EntityField> entityFieldSet = entityFieldMap.values();
        String setString = createColumnValueString(entityFieldSet, entity, ",");
        stringBuilder.append(setString);
        if (!entityMetaData.getPrimaryFieldMap().isEmpty()) {
            Map<String, EntityField> primaryFieldMap = entityMetaData.getPrimaryFieldMap();
            String selectString = createColumnValueString(primaryFieldMap.values(), entity, ",");
            stringBuilder.append(" WHERE ");
            stringBuilder.append(selectString);
        } else
            throw new IllegalArgumentException(entity + " has no primary field");
        return stringBuilder.toString();
    }

    public static String getQuery(DataBaseType dataBaseType, Class entityClass, Condition condition) throws NoSuchFieldException {
        EntityMetaData<?> entityMetaData = EntityMetaDataHelper.getEntityMetaData(entityClass);
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        stringBuilder.append("*");
        stringBuilder.append(" from ");
        stringBuilder.append(entityMetaData.getTableName());
        if (condition != null) {
            stringBuilder.append(" where ");
            stringBuilder.append(condition.toSql(entityClass));
        }
        return stringBuilder.toString();
    }

    public static String getTruncate(DataBaseType dataBaseType, Class<?> clazz) {
        EntityMetaData entityMetaData = EntityMetaDataHelper.getEntityMetaData(clazz);
        String tableName = entityMetaData.getTableName();
        StringBuilder stringBuilder = new StringBuilder("TRUNCATE TABLE ");
        stringBuilder.append(tableName);
        return stringBuilder.toString();
    }

    public static String getCreate(Class<?> clazz) {
        EntityMetaData entityMetaData = EntityMetaDataHelper.getEntityMetaData(clazz);
        String tableName = entityMetaData.getTableName();
        StringBuilder stringBuilder = new StringBuilder();
//        getCreateEnum(entityMetaData, stringBuilder);

        stringBuilder.append("\nCREATE TABLE ");
        stringBuilder.append(tableName);

        StringBuilder fieldsBuilder = new StringBuilder(" ( \n");
        List<String> fieldsStringList = entityMetaData.getEntityFieldStringList();
        fieldsBuilder.append(Joiner.on(",\n").join(fieldsStringList));
        fieldsBuilder.append(");");

        stringBuilder.append(fieldsBuilder);
        return stringBuilder.toString();
    }


    public static <T> String getSave(DataBaseType defaultDatabaseType, Class<T> clazz) {
        EntityMetaData<T> entityMetaData = EntityMetaDataHelper.getEntityMetaData(clazz);
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        stringBuilder.append(entityMetaData.getTableName());
        StringBuilder columnNameBuilder = new StringBuilder("(");
        StringBuilder columnValueBuild = new StringBuilder(" VALUES (");
        List<String> fieldNameList = entityMetaData.getColumnNameList();
        columnNameBuilder.append(Joiner.on(", ").join(fieldNameList));
        columnValueBuild.append(Joiner.on(",").join(fieldNameList.stream().map(s -> "?").collect(Collectors.toList())));
        columnNameBuilder.append(") ");
        columnValueBuild.append(");");
        stringBuilder.append(columnNameBuilder.toString());
        stringBuilder.append(columnValueBuild.toString());
        return stringBuilder.toString();
    }


    public static String getDrop(DataBaseType dataBaseType, Class<?> entityClass) {
        EntityMetaData entityMetaData = EntityMetaDataHelper.getEntityMetaData(entityClass);
        String tableName = entityMetaData.getTableName();
        StringBuilder stringBuilder = new StringBuilder("DROP TABLE ");
        stringBuilder.append(tableName);
        return stringBuilder.toString();
    }

    public static String isTableExist(DataBaseType dataBaseType, Class<?> clazz) {
        EntityMetaData entityMetaData = EntityMetaDataHelper.getEntityMetaData(clazz);
        return "select * from information_schema.tables where table_name='" + entityMetaData.getTableName() + "'";
    }
}
