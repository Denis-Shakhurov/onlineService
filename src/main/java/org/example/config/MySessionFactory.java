package org.example.config;

import org.example.model.Order;
import org.example.model.Service;
import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;

public class MySessionFactory {
    private final String APPLICATION_PROPERTIES = "application.properties";

    public SessionFactory getSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .loadProperties(APPLICATION_PROPERTIES)
                .build();

        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Service.class)
                .getMetadataBuilder()
                .build();

        return metadata.getSessionFactoryBuilder().build();
    }
}
