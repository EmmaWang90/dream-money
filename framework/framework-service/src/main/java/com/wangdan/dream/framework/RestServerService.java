package com.wangdan.dream.framework;

import com.wangdan.dream.commons.serviceProperties.RestServer;

public interface RestServerService {
    void addServer(Class<? extends ServiceBase> restServerClass, RestServer restServerAnnotation);

    void start(ServiceBase serviceBase);
}
