package com.wangdan.dream.rest.impl;

import com.wangdan.dream.commons.serviceProperties.property.PropertyFile;
import com.wangdan.dream.commons.serviceProperties.property.ServiceGroupProperty;
import com.wangdan.dream.framework.ServiceBase;

import java.util.Map;


@PropertyFile("framework.httpService.cfg")
public class HttpConfigureManagerImpl extends ServiceBase {
    public static final String CONTEXT_PATH = "contextPath";
    public static final String DEFAULT_CONTEXT_PATH = "/";
    public static final int DEFAULT_PORT = 8080;
    public static final RestScheme DEFAULT_SCHEME = RestScheme.HTTP;
    public static final String PORT = "port";
    public static final String SCHEME = "scheme";
    private ServiceGroupProperty serviceGroupProperty;

    public HttpConfigureManagerImpl(ServiceBase parent) {
        super(parent);
    }

    public Map<String, String> getServiceProperty(String serverName) {
        return serviceGroupProperty.getGroupProperty(serverName);
    }

    @Override
    public void initialize() {
        super.initialize();
        serviceGroupProperty = new ServiceGroupProperty(super.serviceProperty);
    }

    public void resetRestServerInfo(RestServerInfo restServerInfo) {
        Map<String, String> properties = getServiceProperty(restServerInfo.getServerName());
        if (properties != null) {
            if (properties.containsKey(PORT))
                restServerInfo.setPort(Integer.valueOf(properties.get(PORT)));
            if (properties.containsKey(CONTEXT_PATH))
                restServerInfo.setContextPath(properties.get(CONTEXT_PATH));
            if (properties.containsKey(SCHEME))
                restServerInfo.setRestScheme(RestScheme.valueOf(properties.get(SCHEME)));

            if (restServerInfo.getPort() == null)
                restServerInfo.setPort(DEFAULT_PORT);
            if (restServerInfo.getContextPath() == null)
                restServerInfo.setContextPath(DEFAULT_CONTEXT_PATH);
            if (restServerInfo.getRestScheme() == null)
                restServerInfo.setRestScheme(DEFAULT_SCHEME);
        }
    }
}
