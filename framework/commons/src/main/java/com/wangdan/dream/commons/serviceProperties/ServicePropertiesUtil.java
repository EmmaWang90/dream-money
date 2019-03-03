package com.wangdan.dream.commons.serviceProperties;


import java.io.File;

public class ServicePropertiesUtil {
    public static ServiceProperty getServiceProperty(Class<?> clazz) {
        PropertyFile serviceProperties = clazz.getDeclaredAnnotation(PropertyFile.class);
        if (serviceProperties != null) {
            String fileName = serviceProperties.value();
            if (Environment.isTestEnabled())
                fileName = "./target/test-classes/" + fileName;
            else if (Environment.isKarafEnabled())
                fileName = "./etc/" + fileName;
            File file = new File(fileName);
            ServiceProperty serviceProperty = new ServiceProperty(file);
            serviceProperty.load();
            return serviceProperty;
        } else
            return null;
    }
}
