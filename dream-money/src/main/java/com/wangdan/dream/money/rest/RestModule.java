package com.wangdan.dream.money.rest;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.wangdan.dream.framework.RestServerService;
import com.wangdan.dream.money.DreamMoneyRestService;
import com.wangdan.dream.rest.impl.RestServerManagerServiceImpl;

public class RestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RestServerService.class).to(RestServerManagerServiceImpl.class).in(Singleton.class);
        bind(DreamMoneyRestService.class).to(DreamMoneyRestServiceImpl.class).in(Singleton.class);
    }
}
