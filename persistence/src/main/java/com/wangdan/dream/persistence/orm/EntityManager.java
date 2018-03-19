package com.wangdan.dream.persistence.orm;

import java.sql.Connection;

public interface EntityManager<T> {

    void setConnection(Connection connection);

    boolean save(T entity);
}
