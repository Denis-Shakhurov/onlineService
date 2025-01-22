package org.example.config;

import org.example.model.Order;
import org.example.model.Service;
import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class MySessionFactory {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.DRIVER, "org.postgresql.Driver");
        properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "password");
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
