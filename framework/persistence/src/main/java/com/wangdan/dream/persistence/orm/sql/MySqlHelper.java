package com.wangdan.dream.persistence.orm.sql;

import com.google.common.base.Joiner;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class MySqlHelper {

    public static String getCreate(Class<?> clazz) {
        EntityMetaData entityMetaData = EntityMetaDataHelper.getEntityMetaData(clazz);
        String tableName = entityMetaData.getTableName();
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE IF NOT EXIST");
        stringBuilder.append(tableName);

        StringBuilder fieldsBuilder = new StringBuilder(" ( \n");
        List<String> fieldsStringList = entityMetaData.getEntityFieldList().stream().map(EntityField::getCreateFieldString).collect(toList());
        fieldsBuilder.append(Joiner.on(",\n").join(fieldsStringList));
        fieldsBuilder.append(");");

        stringBuilder.append(fieldsBuilder);
        return stringBuilder.toString();
    }

    public static String isTableExist(Class<?> clazz) {
        EntityMetaData entityMetaData = EntityMetaDataHelper.getEntityMetaData(clazz);
        return "show tables like \"" + entityMetaData.getTableName() + "\";";
    }
}
