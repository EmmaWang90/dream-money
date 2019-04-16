package com.wangdan.dream.persistence.orm;

import com.wangdan.dream.persistence.orm.filter.Condition;

import java.util.List;

public interface EntityManager<T> {

    EntityTableManager getEntityTableManager();

    int delete(Class<T> tClass, Condition condition);

    List<T> query(Class<T> tClass, Condition condition);

    <T> boolean save(T... entity);
}
