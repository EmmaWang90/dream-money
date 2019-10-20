package com.wangdan.dream.money.rest;

import com.google.inject.spi.LinkedKeyBinding;
import com.wangdan.dream.commons.serviceProperties.RestServer;
import com.wangdan.dream.framework.ApplicationBase;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.ServiceBase;
import com.wangdan.dream.money.DreamMoneyApplication;
import com.wangdan.dream.money.DreamMoneyRestService;
import com.wangdan.dream.money.LoadRecordService;

@RestServer(serverName = "default")
public class DreamMoneyRestServiceImpl extends ApplicationBase implements DreamMoneyRestService {
    @Service
    private LoadRecordService loadRecordService;

    public DreamMoneyRestServiceImpl() {
        super(null);
        injectApplicationService();
        start();
    }

    private void injectApplicationService() {
        DreamMoneyApplication.injector.getBindings().forEach(((key, binding) -> {
            if (binding instanceof LinkedKeyBinding) {
                Object instance = DreamMoneyApplication.injector.getInstance(key);
                logger.info("try to inject {} to serviceLocator", instance);
                if (instance instanceof ServiceBase) {
                    ((ServiceBase) instance).start();
                    this.addService(key.getTypeLiteral().getRawType(), (ServiceBase) instance);
                }
                logger.info("succeeded to inject {} to serviceLocator", instance);
            }
        }));
    }

    @Override
    public String getAccounts() {
        return "aa";
    }

    @Override
    public void loadFromFile(String filePath) {
        logger.info("filePath : {}", filePath);
    }
}
