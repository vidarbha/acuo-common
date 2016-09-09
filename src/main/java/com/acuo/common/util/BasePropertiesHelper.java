package com.acuo.common.util;

import com.acuo.common.app.AppId;
import com.acuo.common.app.Configuration;
import com.acuo.common.app.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class BasePropertiesHelper {

    private static final String DEFAULTS_PROPERTIES_TEMPLATE = "/%s.properties";
    private static final String OVERRIDES_PROPERTIES_TEMPLATE = "/%s-%s.properties";

    private final Configuration configuration;

    protected BasePropertiesHelper(Configuration configuration) {
        this.configuration = configuration;
    }

    public Properties getOverrides() {
        return getPropertiesFrom(overrideFilePath());
    }

    public Properties getDefaultProperties() {
        return getPropertiesFrom(defaultFilePath());
    }

    private String overrideFilePath() {
        AppId appId = configuration.getAppId();
        Environment environment = configuration.getEnvironment();
        return String.format(OVERRIDES_PROPERTIES_TEMPLATE, appId.toString(), environment.toString());
    }

    private String defaultFilePath() {
        AppId appId = configuration.getAppId();
        return String.format(DEFAULTS_PROPERTIES_TEMPLATE, appId.toString());
    }

    private Properties getPropertiesFrom(String propertiesFilePath) {
        final Properties properties = new Properties();
        try (final InputStream stream = BasePropertiesHelper.class.getResourceAsStream(propertiesFilePath)) {
            if (stream != null)
                properties.load(stream);
        } catch (IOException e) {
        }
        return properties;
    }
}
