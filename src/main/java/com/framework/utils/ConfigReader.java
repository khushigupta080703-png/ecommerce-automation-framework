package com.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads configuration values from src/main/resources/config.properties.
 * Uses a single static Properties instance so the file is read only once.
 */
public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = "src/main/resources/config.properties";

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties at " + CONFIG_PATH, e);
        }
    }

    private ConfigReader() {
        // utility class - prevent instantiation
    }

    public static String get(String key) {
        // System property (e.g. -Dheadless=true from CI) takes priority over the file
        String systemValue = System.getProperty(key);
        if (systemValue != null) {
            return systemValue;
        }
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing key in config.properties: " + key);
        }
        return value;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
