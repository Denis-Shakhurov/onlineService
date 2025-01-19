package repository;

import dao.ServiceDAO;
import model.Service;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class ServiceRepository extends BaseRepository {
    private final ServiceDAO serviceDAO = new ServiceDAO(sessionFactory);

    public Service save(Service serviceForSave) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Service service;
            session.beginTransaction();
            service = serviceDAO.create(serviceForSave);
            session.getTransaction().commit();
            return service;
        }
    }

    public Optional<Service> findById(int id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Service service;
            session.beginTransaction();
            service = serviceDAO.getById(id);
            session.getTransaction().commit();
            return Optional.ofNullable(service);
        }
    }

    public List<Service> findAllForUser(int userId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            List<Service> services;
            session.beginTransaction();
            services = serviceDAO.findAllForUser(userId);
            session.getTransaction().commit();
            return services;
        }
    }

    public Service update(Service serviceForUpdate) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Service service;
            session.beginTransaction();
            service = serviceDAO.update(serviceForUpdate);
            session.getTransaction().commit();
            return service;
        }
    }
}
