package com.wangdan.dream.commons.serviceProperties.property;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ServiceGroupProperty {
    private Map<String, Map<String, String>> groupProperties = new HashMap<>();

    public ServiceGroupProperty(ServiceProperty serviceProperty) {
        if (serviceProperty != null)
            group(serviceProperty);
    }

    public Map<String, String> getGroupProperty(String groupName) {
        if (groupProperties.containsKey(groupName))
            return groupProperties.get(groupName);
        else
            return new HashMap<>();
    }

    private void group(ServiceProperty serviceProperty) {
        Enumeration keys = serviceProperty.getKeys();
        if (keys != null) {
            while (keys.hasMoreElements()) {
                String key = keys.nextElement().toString();
                if (key.indexOf(".") > 0) {
                    String[] keyArray = key.split("\\.");
                    String group = keyArray[0];
                    String subKey = keyArray[1];
                    if (!groupProperties.containsKey(group))
                        groupProperties.put(group, new HashMap<>());
                    groupProperties.get(group).put(subKey, serviceProperty.getString(key));
                }
            }
        }
    }
}
