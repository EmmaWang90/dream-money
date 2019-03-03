package com.wangdan.dream.commons.serviceProperties;

public class Environment {
    public static final String KarafEnabled = "KarafEnabled";
    public static final String TestEnabled = "TestEnabled";

    public static boolean isKarafEnabled() {
        return Boolean.parseBoolean(System.getProperty(KarafEnabled));
    }

    public static boolean isTestEnabled() {
        return Boolean.parseBoolean(System.getProperty(TestEnabled));
    }

    public static void setTestEnabled(boolean testEnabled) {
        System.setProperty(TestEnabled, Boolean.valueOf(testEnabled).toString());
    }
}
