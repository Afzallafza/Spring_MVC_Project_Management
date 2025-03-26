package org.babor.proj.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    private static AppProperties instance;
    private Properties properties;
    private AppProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized AppProperties getInstance() {
        if (instance == null) {
            instance = new AppProperties();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
