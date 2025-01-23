package org.example.config;

import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

@Getter
public class SessionConfig {
    private final String APPLICATION_PROPERTIES = "/application.properties";

    private final Properties properties = new Properties();

    public SessionConfig() {
        try {
            properties.load(SessionConfig.class.getResourceAsStream(APPLICATION_PROPERTIES));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
