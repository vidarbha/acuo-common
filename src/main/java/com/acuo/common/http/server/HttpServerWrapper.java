/*
 * Copyright (c) 2012 Palomino Labs, Inc.
 */

package com.acuo.common.http.server;

import ch.qos.logback.access.jetty.RequestLogImpl;
import com.acuo.common.app.guice.EventListenerScanner;
import com.acuo.common.app.guice.HandlerScanner;
import com.google.common.collect.Lists;
import com.google.inject.assistedinject.Assisted;
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
public class HttpServerWrapper {

	private static final Logger logger = LoggerFactory.getLogger(HttpServerWrapper.class);

	private final HttpServerWrapperConfig httpServerWrapperConfig;
	private final GuiceFilter filter;
	private final EventListenerScanner eventListenerScanner;
	private final HandlerScanner handlerScanner;
	private final Server server = new Server();

	@Inject
	HttpServerWrapper(@Assisted HttpServerWrapperConfig httpServerWrapperConfig,
					  GuiceFilter filter,
					  EventListenerScanner eventListenerScanner,
					  HandlerScanner handlerScanner) {
		this.httpServerWrapperConfig = httpServerWrapperConfig;
		this.filter = filter;
		this.eventListenerScanner = eventListenerScanner;
		this.handlerScanner = handlerScanner;
	}

	public void start() throws Exception {


		String contextRoot = httpServerWrapperConfig.getContextPath() != null ?
				httpServerWrapperConfig.getContextPath() : "/";

		String applicationPath = httpServerWrapperConfig.getApiPath() != null ?
				httpServerWrapperConfig.getApiPath() : "/api";

		ServletContextHandler servletHandler = new ServletContextHandler(server, contextRoot);

		// add guice servlet filter
		FilterHolder filterHolder = new FilterHolder(filter);
		servletHandler.addFilter(filterHolder, applicationPath + "/*", EnumSet.allOf(DispatcherType.class));

		// Setup the DefaultServlet at "/".
		final ServletHolder defaultServlet = new ServletHolder(new DefaultServlet());
		servletHandler.addServlet(defaultServlet, contextRoot);

		servletHandler.setMaxFormContentSize(httpServerWrapperConfig.getMaxFormContentSize());

		Map<String, String> initParemeters = httpServerWrapperConfig.getInitParemeters();
		for (String key : initParemeters.keySet()) {
			servletHandler.setInitParameter(key, initParemeters.get(key));
		}

		HandlerCollection handlerCollection = new HandlerCollection();

		// add logback-access request log
		RequestLogHandler logHandler = new RequestLogHandler();
		RequestLogImpl logbackRequestLog = new RequestLogImpl();
		logbackRequestLog.setQuiet(httpServerWrapperConfig.isLogbackAccessQuiet());
		if (httpServerWrapperConfig.getAccessLogConfigFileInFilesystem() != null) {
			logger.debug("Loading logback access config from fs path "
					+ httpServerWrapperConfig.getAccessLogConfigFileInFilesystem());
			logbackRequestLog.setFileName(httpServerWrapperConfig.getAccessLogConfigFileInFilesystem());
			logHandler.setRequestLog(logbackRequestLog);
			handlerCollection.addHandler(logHandler);
		} else if (httpServerWrapperConfig.getAccessLogConfigFileInClasspath() != null) {
			logger.debug("Loading logback access config from classpath path "
					+ httpServerWrapperConfig.getAccessLogConfigFileInClasspath());
			logbackRequestLog.setResource(httpServerWrapperConfig.getAccessLogConfigFileInClasspath());
			logHandler.setRequestLog(logbackRequestLog);
			handlerCollection.addHandler(logHandler);
		} else {
			logger.info("No access logging configured.");
		}

		if (!httpServerWrapperConfig.getHttpResourceHandlerConfigs().isEmpty()) {
			ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();

			List<ContextHandler> contextHandlers = Lists.newArrayList();
			for (HttpResourceHandlerConfig httpResourceHandlerConfig : httpServerWrapperConfig
					.getHttpResourceHandlerConfigs()) {
				contextHandlers.add(httpResourceHandlerConfig.buildHandler());
			}

			contextHandlerCollection.setHandlers(contextHandlers.toArray(new Handler[contextHandlers.size()]));

			handlerCollection.addHandler(contextHandlerCollection);
		}

		handlerCollection.addHandler(servletHandler);

		handlerScanner.accept(handlerCollection::addHandler);

		for (ListenerRegistration listener : httpServerWrapperConfig.getServletContextListeners()) {
			listener.apply(servletHandler);
		}

		eventListenerScanner.accept(servletHandler::addEventListener);


		server.setHandler(handlerCollection);

		// add websocket support if configured
		if (httpServerWrapperConfig.isWebSocketSupport()) {
			WebSocketServerContainerInitializer.configureContext(servletHandler);
		}

		for (HttpServerConnectorConfig connectorConfig : httpServerWrapperConfig.getHttpServerConnectorConfigs()) {
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

	/**
	 * @return the config for this wrapper
	 */
	public HttpServerWrapperConfig getHttpServerWrapperConfig() {
		return httpServerWrapperConfig;
	}

	private void addDefaultServlet(ServletContextHandler servletHandler, String resourceBase) {
		// Add Default Servlet (must be named "default")
		ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
		holderDefault.setInitParameter("resourceBase", resourceBase);
		holderDefault.setInitParameter("dirAllowed", "true");
		servletHandler.addServlet(holderDefault, "/");
	}
}
