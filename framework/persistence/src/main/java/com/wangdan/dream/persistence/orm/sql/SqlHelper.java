package com.wangdan.dream.persistence.orm.sql;

import com.google.common.base.Joiner;
import com.wangdan.dream.persistence.orm.DataBaseType;

import java.util.List;

public class SqlHelper {

    public static String getCreate(DataBaseType dataBaseType, Class<?> clazz) {
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
