package com.wangdan.dream.money.rest;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.wangdan.dream.framework.RestServerService;
import com.wangdan.dream.money.LoadRecordService;
import com.wangdan.dream.money.load.LoadRecordServiceImpl;
import com.wangdan.dream.persistence.orm.EntityManager;
import com.wangdan.dream.persistence.orm.impl.EntityManagerImpl;
import com.wangdan.dream.persistence.orm.impl.connection.DatabaseConnectionFactory;
import com.wangdan.dream.rest.impl.RestServerManagerServiceImpl;

public class RestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LoadRecordService.class).to(LoadRecordServiceImpl.class).in(Singleton.class);
        bind(RestServerService.class).to(RestServerManagerServiceImpl.class).in(Singleton.class);
        bind(DatabaseConnectionFactory.class).in(Singleton.class);
        bind(EntityManager.class).to(EntityManagerImpl.class).in(Singleton.class);
//        bind(DreamMoneyRestService.class).to(DreamMoneyRestServiceImpl.class).in(Singleton.class);
    }
}
