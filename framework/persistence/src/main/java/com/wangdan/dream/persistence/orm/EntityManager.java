package com.wangdan.dream.persistence.orm;

import com.wangdan.dream.persistence.orm.filter.Condition;

import java.util.List;

public interface EntityManager<T> {

    void delete(T entity);

    EntityTableManager getEntityTableManager();

    int delete(Class<T> tClass, Condition condition);

    void modify(T entity);

    List<T> query(Class<T> tClass, Condition condition);

    <T> boolean save(T... entity);
}
