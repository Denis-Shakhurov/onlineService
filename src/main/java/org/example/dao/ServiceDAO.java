package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.model.Service;
import org.hibernate.SessionFactory;

import java.util.List;

public class ServiceDAO extends BaseDAO<Service> {

    public ServiceDAO(SessionFactory sessionFactory) {
        super(Service.class, sessionFactory);
    }

    public List<Service> findAllForUser(int userId) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Service> criteria = builder.createQuery(Service.class);
        Root<Service> root = criteria.from(Service.class);
        criteria.select(root).where(builder.equal(root.get("user"), userId));
        return getCurrentSession().createQuery(criteria).getResultList();
    }
}
