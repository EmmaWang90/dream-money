package com.wangdan.dream.commons.serviceProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class ServiceProperty {
    public static Logger logger = LoggerFactory.getLogger(ServiceProperty.class);
    private File file = null;
    private Properties properties = null;
    public ServiceProperty(File file){
        this.file = file;
    }

    public void load(){
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            properties = new Properties();
            properties.load(inputStream);
        } catch (Exception e){
            logger.error("load properties from {}", file.getAbsolutePath(), e);
        }
    }

    public String getString(String key){
        if (properties != null)
            return properties.getProperty(key);
        else
            return "";
    }

    public String getString(String key, String defaultValue){
        if (properties != null)
            return properties.getProperty(key, defaultValue);
        else
            return defaultValue;
    }

    public Enumeration<?> getKeys() {
        if (properties != null)
            return properties.propertyNames();
        return null;
    }

}
