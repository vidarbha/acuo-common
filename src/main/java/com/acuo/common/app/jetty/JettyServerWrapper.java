/*
 * Copyright (c) 2012 Palomino Labs, Inc.
 */

package com.acuo.common.app.jetty;

import ch.qos.logback.access.jetty.RequestLogImpl;
import com.acuo.common.app.guice.EventListenerScanner;
import com.acuo.common.app.guice.HandlerScanner;
import com.google.common.collect.Lists;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;
import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * Runs an embedded jetty server. Sets up the guice servlet filter and request
 * logging.
 */
@ThreadSafe
public class JettyServerWrapper {

	private static final Logger logger = LoggerFactory.getLogger(JettyServerWrapper.class);

	private final JettyServerWrapperConfig jettyServerWrapperConfig;
	private final GuiceFilter filter;
	private final EventListenerScanner eventListenerScanner;
	private final HandlerScanner handlerScanner;
	private final Server server = new Server();

	@Inject
	JettyServerWrapper(JettyServerWrapperConfig jettyServerWrapperConfig,
					   GuiceFilter filter,
					   EventListenerScanner eventListenerScanner,
					   HandlerScanner handlerScanner) {
		this.jettyServerWrapperConfig = jettyServerWrapperConfig;
		this.filter = filter;
		this.eventListenerScanner = eventListenerScanner;
		this.handlerScanner = handlerScanner;
	}

	public void start() throws Exception {

		String contextRoot = jettyServerWrapperConfig.getContextPath() != null ?
				jettyServerWrapperConfig.getContextPath() : "/";

		String applicationPath = jettyServerWrapperConfig.getApiPath() != null ?
				jettyServerWrapperConfig.getApiPath() : "/api";

		ServletContextHandler servletHandler = new ServletContextHandler(server, contextRoot);

		// add guice servlet filter
		FilterHolder filterHolder = new FilterHolder(filter);
		servletHandler.addFilter(filterHolder, applicationPath + "/*", EnumSet.allOf(DispatcherType.class));

		// Setup the DefaultServlet at "/".
		final ServletHolder defaultServlet = new ServletHolder(new DefaultServlet());
		servletHandler.addServlet(defaultServlet, contextRoot);

		servletHandler.setMaxFormContentSize(jettyServerWrapperConfig.getMaxFormContentSize());

		Map<String, String> initParemeters = jettyServerWrapperConfig.getInitParemeters();
		for (String key : initParemeters.keySet()) {
			servletHandler.setInitParameter(key, initParemeters.get(key));
		}

		HandlerCollection handlerCollection = new HandlerCollection();

		// add logback-access request log
		RequestLogHandler logHandler = new RequestLogHandler();
		RequestLogImpl logbackRequestLog = new RequestLogImpl();
		logbackRequestLog.setQuiet(jettyServerWrapperConfig.isLogbackAccessQuiet());
		if (jettyServerWrapperConfig.getAccessLogConfigFileInFilesystem() != null) {
			logger.debug("Loading logback access config from fs path "
					+ jettyServerWrapperConfig.getAccessLogConfigFileInFilesystem());
			logbackRequestLog.setFileName(jettyServerWrapperConfig.getAccessLogConfigFileInFilesystem());
			logHandler.setRequestLog(logbackRequestLog);
			handlerCollection.addHandler(logHandler);
		} else if (jettyServerWrapperConfig.getAccessLogConfigFileInClasspath() != null) {
			logger.debug("Loading logback access config from classpath path "
					+ jettyServerWrapperConfig.getAccessLogConfigFileInClasspath());
			logbackRequestLog.setResource(jettyServerWrapperConfig.getAccessLogConfigFileInClasspath());
			logHandler.setRequestLog(logbackRequestLog);
			handlerCollection.addHandler(logHandler);
		} else {
			logger.info("No access logging configured.");
		}

		if (!jettyServerWrapperConfig.getHandlerConfig().isEmpty()) {
			ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();

			List<ContextHandler> contextHandlers = Lists.newArrayList();
			for (JettyResourceHandlerConfig handlerConfig : jettyServerWrapperConfig
					.getHandlerConfig()) {
				contextHandlers.add(handlerConfig.buildHandler());
			}

			contextHandlerCollection.setHandlers(contextHandlers.toArray(new Handler[contextHandlers.size()]));

			handlerCollection.addHandler(contextHandlerCollection);
		}

		handlerCollection.addHandler(servletHandler);

		handlerScanner.accept(handlerCollection::addHandler);
		eventListenerScanner.accept(servletHandler::addEventListener);

		server.setHandler(handlerCollection);

		// add websocket support if configured
		if (jettyServerWrapperConfig.isWebSocketSupport()) {
			WebSocketServerContainerInitializer.configureContext(servletHandler);
		}

		for (JettyServerConnectorConfig connectorConfig : jettyServerWrapperConfig.getHttpServerConnectorConfigs()) {
			if (connectorConfig.isTls()) {
				SslContextFactory sslContextFactory = new SslContextFactory();
				sslContextFactory.setKeyStore(connectorConfig.getTlsKeystore());
				sslContextFactory.setKeyStorePassword(connectorConfig.getTlsKeystorePassphrase());

				sslContextFactory.setIncludeCipherSuites(connectorConfig.getTlsCipherSuites()
						.toArray(new String[connectorConfig.getTlsCipherSuites().size()]));
				sslContextFactory.setIncludeProtocols(connectorConfig.getTlsProtocols()
						.toArray(new String[connectorConfig.getTlsProtocols().size()]));

				ServerConnector connector = new ServerConnector(server, sslContextFactory);
				connector.setPort(connectorConfig.getListenPort());
				connector.setHost(connectorConfig.getListenHost());
				server.addConnector(connector);
			} else {
				ServerConnector connector = new ServerConnector(server);
				connector.setPort(connectorConfig.getListenPort());
				connector.setHost(connectorConfig.getListenHost());
				server.addConnector(connector);
			}
		}

		server.start();
	}

	public void stop() throws Exception {
		server.stop();
	}

	/**
	 * Provide access to the underlying Jetty Server
	 *
	 * @return the wrapped server instance
	 */
	public Server getServer() {
		return server;
	}
}
