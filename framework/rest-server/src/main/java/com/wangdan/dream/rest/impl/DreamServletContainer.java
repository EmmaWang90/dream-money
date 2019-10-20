package com.wangdan.dream.rest.impl;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.WebConfig;

import javax.servlet.ServletException;

public class DreamServletContainer extends ServletContainer {
    private ServiceLocator serviceLocator;

    public DreamServletContainer(ResourceConfig resourceConfig, ServiceLocator serviceLocator) {
        super(resourceConfig);
        this.serviceLocator = serviceLocator;
    }

    @Override
    protected void init(WebConfig webConfig) throws ServletException {
        webConfig.getServletContext().setAttribute("jersey.config.servlet.context.serviceLocator", this.serviceLocator);
        super.init(webConfig);
    }
}
