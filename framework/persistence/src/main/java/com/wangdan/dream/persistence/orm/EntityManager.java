package com.wangdan.dream.persistence.orm;

public interface EntityManager<T> {

    EntityTableManager getEntityTableManager();

    boolean save(T entity);
}
