package com.wangdan.dream.money;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wangdan.dream.framework.FrameworkModule;
import com.wangdan.dream.framework.ServiceTracker;
import com.wangdan.dream.money.rest.DreamMoneyRestServiceImpl;
import com.wangdan.dream.money.rest.RestModule;

public class DreamMoneyApplication {
    public static void main(String[] args) throws InterruptedException {
        Injector injector = Guice.createInjector(new FrameworkModule(), new RestModule());
        DreamMoneyRestService dreamMoneyRestService = injector.getInstance(DreamMoneyRestService.class);
        ServiceTracker serviceTracker = injector.getInstance(ServiceTracker.class);
        DreamMoneyRestServiceImpl dreamMoneyRestServiceImpl = (DreamMoneyRestServiceImpl) dreamMoneyRestService;
        serviceTracker.startService(dreamMoneyRestServiceImpl);
        Thread.sleep(500000);
    }
}
