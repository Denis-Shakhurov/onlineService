package dao;

import model.Service;
import org.hibernate.SessionFactory;

public class ServiceDAO extends BaseDAO<Service> {

    public ServiceDAO(SessionFactory sessionFactory) {
        super(Service.class, sessionFactory);
    }
}
