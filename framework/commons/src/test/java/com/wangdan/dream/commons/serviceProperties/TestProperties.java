package com.wangdan.dream.commons.serviceProperties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@PropertyFile("./src/test/resources/test.cfg")
public class TestProperties {
    @Test
    public void test(){
        ServiceProperty serviceProperty = ServicePropertiesUtil.getServiceProperty(TestProperties.class);
        assertEquals(serviceProperty.getString("dream"), "money");
    }

}
