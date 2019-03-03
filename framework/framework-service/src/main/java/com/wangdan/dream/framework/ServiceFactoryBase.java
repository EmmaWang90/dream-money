package com.wangdan.dream.framework;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

abstract public class ServiceFactoryBase<T> extends ServiceBase {
    private Map<String, T> serviceMap = new HashMap<>();
    private Map<String, Properties> servicePropertyMap = new HashMap<>();

    public ServiceFactoryBase(ServiceBase parent) {
        super(parent);
    }

    protected abstract T createService(String serviceName, Properties properties);

    private void createServicePool() {
        Enumeration<?> servicePropertyKeys = super.serviceProperty.getKeys();
        if (servicePropertyKeys != null) {
            while (servicePropertyKeys.hasMoreElements()) {
                String key = (String) servicePropertyKeys.nextElement();
                if (key.contains(".")) {
                    String[] keyArray = key.split(".");
                    if (!servicePropertyMap.containsKey(keyArray[0]))
                        servicePropertyMap.put(keyArray[0], new Properties());
                    servicePropertyMap.get(keyArray[0]).put(keyArray[1], serviceProperty.getString(key));
                }
            }
        }
        for (String serviceName : servicePropertyMap.keySet()) {
            T service = createService(serviceName, servicePropertyMap.get(serviceName));
            serviceMap.put(serviceName, service);
        }
    }

    @Override
    public void start() {
        super.start();
        createServicePool();
    }

}
