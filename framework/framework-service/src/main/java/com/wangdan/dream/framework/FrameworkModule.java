package com.wangdan.dream.framework;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class FrameworkModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ServiceTracker.class).to(ServiceTrackerImpl.class).in(Singleton.class);
    }
}
