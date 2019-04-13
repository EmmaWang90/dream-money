package com.wangdan.dream.persistence.orm.sql;

import java.util.HashMap;
import java.util.Map;

public class EntityMetaDataHelper {
    private static Map<Class<?>, EntityMetaData> entityMetaDataMap = new HashMap<>();

    private EntityMetaDataHelper() {

    }

    public static <T> EntityMetaData<T> getEntityMetaData(Class<T> clazz) {
        if (!entityMetaDataMap.containsKey(clazz)) {
            EntityMetaData<T> entityMetaData = new EntityMetaData<T>(clazz);
            entityMetaData.initialize();
            entityMetaDataMap.put(clazz, entityMetaData);
        }
        return entityMetaDataMap.get(clazz);

    }
}
