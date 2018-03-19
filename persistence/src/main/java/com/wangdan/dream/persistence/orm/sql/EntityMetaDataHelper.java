package com.wangdan.dream.persistence.orm.sql;

import java.util.HashMap;
import java.util.Map;

public class EntityMetaDataHelper {
    private static Map<Class<?>, EntityMetaData> entityMetaDataMap = new HashMap<>();

    private EntityMetaDataHelper() {

    }

    public EntityMetaData getEntityMetaData(Class<?> clazz) {
        if (!entityMetaDataMap.containsKey(clazz)) {
            EntityMetaData entityMetaData = new EntityMetaData(clazz);
        }
        return entityMetaDataMap.get(clazz);

    }
}
