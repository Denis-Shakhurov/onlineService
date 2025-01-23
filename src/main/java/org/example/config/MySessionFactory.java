package org.example.config;

import org.example.model.Order;
import org.example.model.Service;
import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class MySessionFactory {
    private final String DIALECT_KEY = "db.dialect";
    private final String DRIVER_KEY = "db.driver";
    private final String URL_KEY = "db.url";
    private final String USER_KEY = "db.user";
    private final String PASSWORD_KEY = "db.password";
    private SessionFactory sessionFactory;
    private SessionConfig sessionConfig;

    public SessionFactory getSessionFactory() {
        sessionConfig = new SessionConfig();
        Properties properties = sessionConfig.getProperties();

        properties.put(Environment.DIALECT, sessionConfig.getProperty(DIALECT_KEY));
        properties.put(Environment.DRIVER, sessionConfig.getProperty(DRIVER_KEY));
        properties.put(Environment.URL, sessionConfig.getProperty(URL_KEY));
        properties.put(Environment.USER, sessionConfig.getProperty(USER_KEY));
        properties.put(Environment.PASS, sessionConfig.getProperty(PASSWORD_KEY));
        properties.put(Environment.SHOW_SQL, true);
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Service.class)
                .addAnnotatedClass(Order.class)
                .buildSessionFactory();

        return sessionFactory;
    }
}
