package com.wangdan.dream.rest.impl;

import com.wangdan.dream.commons.serviceProperties.RestServer;
import com.wangdan.dream.framework.InjectService;
import com.wangdan.dream.framework.RestServerService;
import com.wangdan.dream.framework.Service;
import com.wangdan.dream.framework.ServiceBase;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;
import org.glassfish.jersey.jetty.JettyHttpContainer;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.concurrent.ConcurrentHashMap;

@InjectService(implementation = HttpConfigureManagerImpl.class)
public class RestServerManagerServiceImpl extends ServiceBase implements RestServerService {
    @Service
    private HttpConfigureManagerImpl httpConfigureManager;
    private ConcurrentHashMap<Class<?>, RestServerInfo> restServerInfoConcurrentHashMap = new ConcurrentHashMap<>();

    public RestServerManagerServiceImpl() {
        super(null);
    }

    @Override
    public void addServer(Class<? extends ServiceBase> restServerClass, RestServer restServerAnnotation) {
        RestServerInfo restServerInfo = createRestServerInfo(restServerClass, restServerAnnotation);
        createServer(restServerInfo);
        restServerInfoConcurrentHashMap.put(restServerClass, restServerInfo);
    }

    private RestServerInfo createRestServerInfo(Class<? extends ServiceBase> restServerClass, RestServer restServerAnnotation) {
        RestServerInfo restServerInfo = new RestServerInfo();
        restServerInfo.setResourceClass(restServerClass);
        restServerInfo.setServerName(restServerAnnotation.serverName());
        return restServerInfo;
    }

    private Server createServer() {
        ExecutorThreadPool executorThreadPool = new ExecutorThreadPool(20, 10);
        return new Server(executorThreadPool);
    }

    private void createServer(RestServerInfo restServerInfo) {
        httpConfigureManager.resetRestServerInfo(restServerInfo);
        ResourceConfig resourceConfig = new ResourceConfig(restServerInfo.getResourceClass());
        JettyHttpContainer container = ContainerFactory.createContainer(JettyHttpContainer.class, resourceConfig);
        Server server = createServer();
        setServerConnector(restServerInfo, server);
        server.setHandler(container);
        try {
            server.start();
        } catch (Exception e) {
            logger.error("failed to start rest for {}", restServerInfo, e);
        }
    }

    private void setServerConnector(RestServerInfo restServerInfo, Server server) {
        HttpConfiguration config = new HttpConfiguration();
        ServerConnector https;
        SslContextFactory sslContextFactory = null;
//        if (sslContextFactory != null) {
//            config.setSecureScheme("https");
//            config.setSecurePort(restServerInfo.getPort());
//            config.addCustomizer(new SecureRequestCustomizer());
//            https = new ServerConnector(server, new ConnectionFactory[]{new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(config)});
//            https.setPort(restServerInfo.getPort());
//        } else {
        https = new ServerConnector(server, new ConnectionFactory[]{new HttpConnectionFactory(config)});
        https.setPort(restServerInfo.getPort());
//        }
        server.setConnectors(new Connector[]{https});
    }

    @Override
    public void start(ServiceBase serviceBase) {
        RestServer restServerAnnotation = serviceBase.getClass().getDeclaredAnnotation(RestServer.class);
        if (restServerAnnotation != null) {
            try {
                addServer(serviceBase.getClass(), restServerAnnotation);

            } catch (Exception e) {
                logger.error("failed start rest server for {}", serviceBase, e);
            }
        }
    }
}
