package com.wangdan.dream.persistence.orm;

public interface EntityManager<T> {

    boolean save(T entity);
}
