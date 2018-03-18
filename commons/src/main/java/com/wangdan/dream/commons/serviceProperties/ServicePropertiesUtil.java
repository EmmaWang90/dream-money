package com.wangdan.dream.commons.serviceProperties;

import java.io.File;

public class ServicePropertiesUtil {
    public static ServiceProperty getServiceProperty(Class<?> clazz){
        Properties serviceProperties = clazz.getDeclaredAnnotation(Properties.class);
        if (serviceProperties != null)
        {
            File file = new File(serviceProperties.value());
            ServiceProperty serviceProperty = new ServiceProperty(file);
            serviceProperty.load();
            return serviceProperty;
        }else
            return null;
    }
}
