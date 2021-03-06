package com.wangdan.dream.framework;

import com.wangdan.dream.commons.serviceProperties.property.Property;
import com.wangdan.dream.commons.serviceProperties.property.PropertyFile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@PropertyFile("./src/test/resources/test.cfg")
@InjectService(accessClass = HelpService.class, implementation = HelpService.class)
public class TestInjectService extends ServiceBase {
    @Property(name = "dream")
    private String dream;
    @Service
    private HelpService helpService;

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
