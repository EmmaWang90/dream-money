package com.wangdan.dream.persistence.orm.sql;

import com.google.common.base.Joiner;
import com.wangdan.dream.persistence.orm.DataBaseType;
import com.wangdan.dream.persistence.orm.filter.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlHelper {
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

    public static String getQuery(DataBaseType dataBaseType, Class entityClass, Condition condition) {
        EntityMetaData<?> entityMetaData = EntityMetaDataHelper.getEntityMetaData(entityClass);
        StringBuilder stringBuilder = new StringBuilder("SELECT * from ");
        stringBuilder.append(entityMetaData.getTableName());
        if (condition.getFilterGroup() != null) {
            stringBuilder.append(" where ");
            stringBuilder.append(condition.getFilterGroup().toSql());
        }
        if (condition.getOrder() != null) {
            //TODO
        }
        if (condition.getRange() != null) {
            //TODO
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
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
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
