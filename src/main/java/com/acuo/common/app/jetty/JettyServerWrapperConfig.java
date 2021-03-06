/*
 * Copyright (c) 2012 Palomino Labs, Inc.
 */

package com.acuo.common.app.jetty;


import com.acuo.common.app.main.ServerConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

/**
 * Config info for {@link JettyServerWrapper}.
 */
public final class JettyServerWrapperConfig implements ServerConfig {

	private String contextPath = null;
	private String apiPath = null;
	private int maxFormContentSize = -1;
	private boolean logbackAccessQuiet = true;
	private boolean supportWebSocket = false;
	private final Map<String, String> initParamters = newHashMap();
	private final List<JettyServerConnectorConfig> connectorConfigs = newArrayList();
	private final List<JettyResourceHandlerConfig> handlerConfig = newArrayList();
	private final List<ListenerRegistration> servletContextListeners = newArrayList();

	@Nullable
	private String accessLogConfigFileInClasspath = "/" + getClass().getPackage().getName().replace('.', '/')
			+ "/pl-default-logback-access.xml";

	@Nullable
	private String accessLogConfigFileInFilesystem = null;

	@Nonnull
	public JettyServerWrapperConfig withContextPath(String contextPath) {
		setContextPath(contextPath);
		return this;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Nonnull
	public JettyServerWrapperConfig withApiPath(String apiPath) {
		setApiPath(apiPath);
		return this;
	}

	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	public int getMaxFormContentSize() {
		return maxFormContentSize;
	}

	/**
	 * @param maxFormContentSize
	 *            max form content size
	 * @return this
	 * @see JettyServerWrapperConfig#setMaxFormContentSize(int)
	 */
	@Nonnull
	public JettyServerWrapperConfig withMaxFormContentSize(int maxFormContentSize) {
		setMaxFormContentSize(maxFormContentSize);
		return this;
	}

	/**
	 * Default is -1 (which means that it will be ignored and the server
	 * default, currently 200k, will be used)
	 *
	 * @param maxFormContentSize
	 *            content size in bytes, -1 to use Jetty default
	 */
	public void setMaxFormContentSize(int maxFormContentSize) {
		this.maxFormContentSize = maxFormContentSize;
	}

	public boolean isLogbackAccessQuiet() {
		return logbackAccessQuiet;
	}

	/**
	 * @param logbackAccessQuiet
	 *            logback access 'quiet' mode setting
	 * @return this
	 * @see JettyServerWrapperConfig#setLogbackAccessQuiet(boolean)
	 */
	@Nonnull
	public JettyServerWrapperConfig withLogbackAccessQuiet(boolean logbackAccessQuiet) {
		setLogbackAccessQuiet(logbackAccessQuiet);
		return this;
	}

	/**
	 * Sets the Logback Access request log handler's quiet flag.
	 *
	 * @param logbackAccessQuiet
	 *            true to muffle Logback status messages on startup, false to
	 *            allow them.
	 */
	public void setLogbackAccessQuiet(boolean logbackAccessQuiet) {
		this.logbackAccessQuiet = logbackAccessQuiet;
	}

	public JettyServerWrapperConfig withWebSocketSupport() {
		setWebSocketSupport(true);
		return this;
	}

	public void setWebSocketSupport(boolean webSocketSupport) {
		this.supportWebSocket = webSocketSupport;
	}

	public boolean isWebSocketSupport() {
		return supportWebSocket;
	}

	public Map<String, String> getInitParemeters() {
		return initParamters;
	}

	public void addInitParameter(String key, String value) {
		initParamters.put(key, value);
	}

	@Nonnull
	public List<JettyServerConnectorConfig> getHttpServerConnectorConfigs() {
		return connectorConfigs;
	}

	/**
	 * @param config
	 *            connector config
	 * @return this
	 * @see JettyServerWrapperConfig#addHttpServerConnectorConfig(JettyServerConnectorConfig)
	 */
	@Nonnull
	public JettyServerWrapperConfig withHttpServerConnectorConfig(@Nonnull JettyServerConnectorConfig config) {
		addHttpServerConnectorConfig(config);
		return this;
	}

	/**
	 * Add config for a connector.
	 *
	 * @param connectorConfig
	 *            a connector config
	 */
	public void addHttpServerConnectorConfig(@Nonnull JettyServerConnectorConfig connectorConfig) {
		connectorConfigs.add(checkNotNull(connectorConfig));
	}

	@Nonnull
	public List<JettyResourceHandlerConfig> getHandlerConfig() {
		return handlerConfig;
	}

	/**
	 * @param handlerConfig
	 *            resource handler config
	 * @return this
	 * @see JettyServerWrapperConfig#addResourceHandlerConfig(JettyResourceHandlerConfig)
	 */
	public JettyServerWrapperConfig withResourceHandlerConfig(
			@Nonnull JettyResourceHandlerConfig handlerConfig) {
		addResourceHandlerConfig(handlerConfig);
		return this;
	}

	/**
	 * Add a JettyResourceHandlerConfig for serving static resources.
	 *
	 * @param handlerConfig
	 *            a resource handler config
	 */
	public void addResourceHandlerConfig(@Nonnull JettyResourceHandlerConfig handlerConfig) {
		this.handlerConfig.add(handlerConfig);
	}

	@Nonnull
	List<ListenerRegistration> getServletContextListeners() {
		return servletContextListeners;
	}

	/**
	 * @param listener
	 *            context listener
	 */
	public void addServletContextListener(ServletContextListener listener) {
		servletContextListeners.add(ListenerRegistration.forListener(listener));
	}

	/**
	 * If you want Guice to construct your ServletContextListener instance,
	 * consider using {@link BinderProviderCapture} to make it easier to get a
	 * Provider for your ServletContextListener implementation. This will allow
	 * Guice to check at module initialization time that all needed bindings are
	 * available, leading to earlier errors (if any are imminent).
	 *
	 * @param listenerProvider
	 *            context listener provider
	 */
	public void addServletContextListenerProvider(Provider<? extends ServletContextListener> listenerProvider) {
		servletContextListeners.add(ListenerRegistration.forListenerProvider(listenerProvider));
	}

	@Nullable
	public String getAccessLogConfigFileInClasspath() {
		return accessLogConfigFileInClasspath;
	}

	/**
	 * @param accessLogConfigFileInClasspath
	 *            classpath path to access log config file
	 * @return this
	 * @see JettyServerWrapperConfig#setAccessLogConfigFileInClasspath(String)
	 */
	@Nonnull
	public JettyServerWrapperConfig withAccessLogConfigFileInClasspath(@Nullable String accessLogConfigFileInClasspath) {
		setAccessLogConfigFileInClasspath(accessLogConfigFileInClasspath);
		return this;
	}

	/**
	 * This is checked after the value set in
	 * {@link JettyServerWrapperConfig#setAccessLogConfigFileInFilesystem(String)}
	 * . The default value points to a bundled config file that prints combined
	 * access log to the console's stdout.
	 *
	 * Setting this nulls the accessLogConfigFileInFilesystem.
	 *
	 * @param accessLogConfigFileInClasspath
	 *            Classpath path to logback-access config file.
	 */
	public void setAccessLogConfigFileInClasspath(@Nullable String accessLogConfigFileInClasspath) {
		this.accessLogConfigFileInFilesystem = null;
		this.accessLogConfigFileInClasspath = accessLogConfigFileInClasspath;
	}

	@Nullable
	public String getAccessLogConfigFileInFilesystem() {
		return accessLogConfigFileInFilesystem;
	}

	/**
	 * @param accessLogConfigFileInFilesystem
	 *            filesystem path to access log config file
	 * @return this
	 * @see JettyServerWrapperConfig#setAccessLogConfigFileInFilesystem(String)
	 */
	@Nonnull
	public JettyServerWrapperConfig withAccessLogConfigFileInFilesystem(
			@Nullable String accessLogConfigFileInFilesystem) {
		setAccessLogConfigFileInFilesystem(accessLogConfigFileInFilesystem);
		return this;
	}

	/**
	 * If this is not set, the value set in
	 * {@link JettyServerWrapperConfig#setAccessLogConfigFileInClasspath(String)}
	 * is used.
	 *
	 * Setting this nulls the accessLogConfigFileInClasspath.
	 *
	 * @param accessLogConfigFileInFilesystem
	 *            Filesystem path to logback-access config file.
	 */
	public void setAccessLogConfigFileInFilesystem(@Nullable String accessLogConfigFileInFilesystem) {
		this.accessLogConfigFileInClasspath = null;
		this.accessLogConfigFileInFilesystem = accessLogConfigFileInFilesystem;
	}
}
