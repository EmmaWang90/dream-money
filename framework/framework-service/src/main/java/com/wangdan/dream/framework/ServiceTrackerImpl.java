package com.wangdan.dream.framework;

import javax.inject.Inject;

public class ServiceTrackerImpl extends ServiceBase implements ServiceTracker {
    @Inject
    private RestServerService restServerService;

    public ServiceTrackerImpl() {
        super(null);
    }

    private void startRestService(ServiceBase serviceBase) {
        if (restServerService != null)
            restServerService.start(serviceBase);
    }

    public void startService(ServiceBase serviceBase) {
        startRestService(serviceBase);
    }
}
