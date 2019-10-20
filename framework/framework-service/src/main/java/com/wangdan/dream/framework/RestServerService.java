package com.wangdan.dream.framework;

import com.wangdan.dream.commons.serviceProperties.RestServer;
import org.glassfish.hk2.api.ServiceLocator;

public interface RestServerService {
    void addServer(ServiceBase serviceBase, RestServer restServerAnnotation, ServiceLocator serviceLocator);

    void start(ServiceBase serviceBase, ServiceLocator serviceLocator);
}
