package com.wangdan.dream.framework;

import com.wangdan.dream.commons.serviceProperties.Property;
import com.wangdan.dream.commons.serviceProperties.PropertyFile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@PropertyFile("./src/test/resources/test.cfg")
@InjectService(accessClass = HelpService.class, implementation = HelpService.class)
public class TestInjectService extends ServiceBase {
    @Service
    private HelpService helpService;
    @Property("dream")
    private String dream;

    public TestInjectService() {
        super(null);
    }

    public HelpService getHelpService() {
        return helpService;
    }

    @Test
    public void test() {
        TestInjectService testInjectService = new TestInjectService();
        testInjectService.start();
        assertNotNull(testInjectService.getHelpService());
        assertEquals(testInjectService.dream, "money");
    }
}
