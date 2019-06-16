package com.wangdan.dream.commons.serviceProperties;

import com.wangdan.dream.commons.serviceProperties.property.ServiceGroupProperty;
import com.wangdan.dream.commons.serviceProperties.property.ServiceProperty;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGroupProperty {
    @Test
    public void test() {
        ServiceProperty serviceProperty = new ServiceProperty(new File("./src/test/resources/testGroup.cfg"));
        serviceProperty.load();
        ServiceGroupProperty serviceGroupProperty = new ServiceGroupProperty(serviceProperty);
        Map<String, String> aGroupProperty = serviceGroupProperty.getGroupProperty("a");
        assertEquals(aGroupProperty.keySet().size(), 2);
        assertEquals(aGroupProperty.get("name"), "AA");
        assertEquals(aGroupProperty.get("value"), "aa");
    }
}
