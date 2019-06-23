package com.wangdan.dream.framework;

import javax.inject.Inject;

public class ApplicationBase extends ServiceBase {
    public ApplicationBase(ServiceBase parent) {
        super(parent);
        initialize();
    }

    @Inject
    private void initializeOtherService(ServiceTracker serviceTracker) {
        serviceTracker.startService(this);
    }

    @Override
    protected void initializeService() {
        super.initializeService();
    }

}
