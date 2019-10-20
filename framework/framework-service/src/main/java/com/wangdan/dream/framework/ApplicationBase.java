package com.wangdan.dream.framework;

public class ApplicationBase extends ServiceBase {
    public ApplicationBase(ServiceBase parent) {
        super(parent);
        initialize();
    }

    @Override
    protected void initializeService() {
        super.initializeService();
    }

}
