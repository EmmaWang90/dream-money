package com.wangdan.dream.framework.test;

import com.wangdan.dream.commons.serviceProperties.Environment;
import com.wangdan.dream.framework.ServiceBase;

public class ServiceTestBase extends ServiceBase{

    public ServiceTestBase(ServiceBase parent) {
        super(parent);
    }

    @Override
    public void start(){
        Environment.setTestEnabled(true);
        super.start();
    }
}
