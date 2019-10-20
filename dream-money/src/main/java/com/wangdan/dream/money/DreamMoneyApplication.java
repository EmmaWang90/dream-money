package com.wangdan.dream.money;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.spi.LinkedKeyBinding;
import com.wangdan.dream.framework.FrameworkModule;
import com.wangdan.dream.framework.RestServerService;
import com.wangdan.dream.framework.ServiceBase;
import com.wangdan.dream.money.rest.DreamMoneyRestServiceImpl;
import com.wangdan.dream.money.rest.RestModule;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.internal.ServiceLocatorFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DreamMoneyApplication {
    public static final Logger logger = LoggerFactory.getLogger(DreamMoneyApplication.class);
    public static void main(String[] args) throws InterruptedException {
        Injector injector = Guice.createInjector(new FrameworkModule(), new RestModule());
        ServiceLocator serviceLocator = ServiceLocatorFactoryImpl.getInstance().create("dream-rest");
        //TODO 需要优化注入顺序，把被依赖的服务先注入进去
        injector.getBindings().forEach(((key, binding) -> {
            if (binding instanceof LinkedKeyBinding) {
                Object instance = injector.getInstance(key);
                logger.info("try to inject {} to serviceLocator", instance);
                if (instance instanceof ServiceBase)
                    ((ServiceBase) instance).start();
                serviceLocator.inject(instance);
                logger.info("succeeded to inject {} to serviceLocator", instance);
            }
        }));
        RestServerService restServerService = injector.getInstance(RestServerService.class);
        restServerService.start(new DreamMoneyRestServiceImpl(), serviceLocator);
        Thread.sleep(500000);
    }
}
