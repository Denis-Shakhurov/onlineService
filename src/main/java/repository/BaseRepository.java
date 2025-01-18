package repository;

import config.MySessionFactory;
import org.hibernate.SessionFactory;

public class BaseRepository {
    private final MySessionFactory mySessionFactory = new MySessionFactory();
    public SessionFactory sessionFactory = mySessionFactory.getSessionFactory();
}
