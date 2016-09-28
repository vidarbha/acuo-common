package com.acuo.common.app;

public class Configuration {

	private final AppId appId;
	private final Environment environment;
	private final SecurityKey securityKey;

	private Configuration(Builder builder) {
		this.appId = builder.appId;
		this.environment = builder.environment;
		this.securityKey = builder.securityKey;
	}

	public static Builder builder(AppId appId) {
		return new Builder(appId);
	}

	public AppId getAppId() {
		return appId;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public SecurityKey getSecurityKey() {
		return securityKey;
	}

	public static class Builder {

		private final AppId appId;
		private Environment environment = Environment.TEST;
		private SecurityKey securityKey = null;

		public Builder(AppId appId) {
			this.appId = appId;
		}

		public Builder with(Environment environment) {
			this.environment = environment;
			return this;
		}

		public Builder with(SecurityKey securityKey) {
			this.securityKey = securityKey;
			return this;
		}

		public Configuration build() {
			return new Configuration(this);
		}
	}

}
