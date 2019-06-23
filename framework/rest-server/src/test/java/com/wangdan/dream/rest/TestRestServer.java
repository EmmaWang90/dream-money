package com.wangdan.dream.rest;

import com.wangdan.dream.rest.impl.RestScheme;
import com.wangdan.dream.rest.impl.RestServerInfo;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;
import org.glassfish.jersey.jetty.JettyHttpContainer;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.jetty.internal.LocalizationMessages;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class TestRestServer {
    public static final Logger logger = LoggerFactory.getLogger(TestRestServer.class);

    private ConnectionFactory createConnectionFactory(RestServerInfo restServerInfo) {
        ConnectionFactory connectionFactory = null;
        if (restServerInfo.getRestScheme() == RestScheme.HTTP) {
            connectionFactory = createHttpConnectionFactory(restServerInfo);
        }
        return connectionFactory;
    }

    private ConnectionFactory createHttpConnectionFactory(RestServerInfo restServerInfo) {
        HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory();
        httpConnectionFactory.setInputBufferSize(2048);
        return httpConnectionFactory;
    }

    @Test
    public void testFunction() throws InterruptedException {
        RestServerInfo restServerInfo = new RestServerInfo();
        restServerInfo.setResourceClass(MyResource.class);
        restServerInfo.setRestScheme(RestScheme.HTTP);
        restServerInfo.setContextPath("/");
        restServerInfo.setPort(9090);

        ResourceConfig resourceConfig = new ResourceConfig(restServerInfo.getResourceClass());
        JettyHttpContainer container = ContainerFactory.createContainer(JettyHttpContainer.class, resourceConfig);

        ExecutorThreadPool executorThreadPool = new ExecutorThreadPool(20, 10);
        Server server = new Server(executorThreadPool);
        HttpConfiguration config = new HttpConfiguration();
        ServerConnector https;
        SslContextFactory sslContextFactory = null;
        if (sslContextFactory != null) {
            config.setSecureScheme("https");
            config.setSecurePort(restServerInfo.getPort());
            config.addCustomizer(new SecureRequestCustomizer());
            https = new ServerConnector(server, new ConnectionFactory[]{new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(config)});
            https.setPort(restServerInfo.getPort());
            server.setConnectors(new Connector[]{https});
        } else {
            https = new ServerConnector(server, new ConnectionFactory[]{new HttpConnectionFactory(config)});
            https.setPort(restServerInfo.getPort());
            server.setConnectors(new Connector[]{https});
        }

        if (container != null) {
            server.setHandler(container);
        }

        try {
            server.start();
        } catch (Exception var10) {
            throw new ProcessingException(LocalizationMessages.ERROR_WHEN_CREATING_SERVER(), var10);
        }
        Thread.sleep(50000000);
    }

    @Test
    public void testSimpleJettyRest() throws Exception {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig config = new ResourceConfig(MyResource.class);
        Server server = JettyHttpContainerFactory.createServer(baseUri, config);
        server.start();
    }
}
