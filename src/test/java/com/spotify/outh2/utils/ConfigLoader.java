package com.spotify.outh2.utils;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getConfigProperty(String propertyKey) {
        String prop = properties.getProperty(propertyKey);
        if (prop != null) return prop;
        else throw new RuntimeException("property" + propertyKey + " is not specified in the config properties file");
    }

}
