package org.example.repository;

import org.example.config.MySessionFactory;
import org.hibernate.SessionFactory;

public class BaseRepository {
    private final MySessionFactory mySessionFactory = new MySessionFactory();
    protected SessionFactory sessionFactory = mySessionFactory.getSessionFactory();
}
