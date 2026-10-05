package com.bottomless.utils;

import com.bottomless.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH)) {
            properties.load(fis);
            log.info("Configuration properties loaded successfully from: {}", FrameworkConstants.CONFIG_FILE_PATH);
        } catch (IOException e) {
            log.warn("Could not load config.properties, falling back to system defaults. Reason: {}", e.getMessage());
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        // System property takes precedence over config.properties
        String systemProp = System.getProperty(key);
        if (systemProp != null && !systemProp.trim().isEmpty()) {
            return systemProp.trim();
        }
        return properties.getProperty(key, "").trim();
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value.isEmpty() ? defaultValue : value;
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        if (value.isEmpty()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
}
