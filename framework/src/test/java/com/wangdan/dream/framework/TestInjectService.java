package com.wangdan.dream.framework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@InjectService(accessClass = HelpService.class, implementation = HelpService.class)
public class TestInjectService extends ServiceBase {
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
    }
}
