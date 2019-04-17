package com.wangdan.dream.persistence.orm.sql;

import com.google.common.base.Joiner;

import java.util.List;

public class PostgreHelper implements DatabaseSqlHelper {

    @Override
    public String getCreateIndex(Class<?> clazz) {

        EntityMetaData entityMetaData = EntityMetaDataHelper.getEntityMetaData(clazz);
        List<String> indexStringList = entityMetaData.getIndexStringList();
        if (indexStringList != null && indexStringList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("create index ");
            stringBuilder.append(clazz.getCanonicalName());
            stringBuilder.append(" on ");
            stringBuilder.append(entityMetaData.getTableName());
            stringBuilder.append("( ");
            stringBuilder.append(Joiner.on(", ").join(indexStringList));
            stringBuilder.append(" );");
            return stringBuilder.toString();
        }
        return null;
    }

    @Override
    public String getCreateTable(Class<?> clazz) {
        return null;
    }
}
