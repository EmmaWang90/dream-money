package com.wangdan.dream.money;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wangdan.dream.framework.FrameworkModule;
import com.wangdan.dream.framework.RestServerService;
import com.wangdan.dream.money.rest.DreamMoneyRestServiceImpl;
import com.wangdan.dream.money.rest.RestModule;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.internal.ServiceLocatorFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DreamMoneyApplication {
    public static final Logger logger = LoggerFactory.getLogger(DreamMoneyApplication.class);
    public static Injector injector = null;
    public static void main(String[] args) throws InterruptedException {
        injector = Guice.createInjector(new FrameworkModule(), new RestModule());
        ServiceLocator serviceLocator = ServiceLocatorFactoryImpl.getInstance().create("dream-rest");

        RestServerService restServerService = injector.getInstance(RestServerService.class);
        restServerService.start(new DreamMoneyRestServiceImpl(), serviceLocator);
        Thread.sleep(500000);
    }
}
