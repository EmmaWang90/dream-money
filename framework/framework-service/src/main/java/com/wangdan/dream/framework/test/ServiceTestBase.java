package com.wangdan.dream.framework.test;

import com.wangdan.dream.commons.serviceProperties.Environment;
import com.wangdan.dream.framework.ServiceBase;
import org.junit.jupiter.api.BeforeEach;

public class ServiceTestBase extends ServiceBase {
    public ServiceTestBase() {
        this(null);
    }

    public ServiceTestBase(ServiceBase parent) {
        super(parent);
    }

    @Override
    @BeforeEach
    public void start() {
        Environment.setTestEnabled(true);
        super.start();
    }
}
