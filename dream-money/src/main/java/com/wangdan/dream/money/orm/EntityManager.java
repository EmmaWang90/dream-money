package com.wangdan.dream.money.orm;

public interface EntityManager<T> {

    boolean save(T entity);
}
