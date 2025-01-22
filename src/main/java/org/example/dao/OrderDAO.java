package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.model.Order;
import org.example.model.Service;
import org.hibernate.SessionFactory;

import java.util.List;

public class OrderDAO extends BaseDAO<Order> {

    public OrderDAO(SessionFactory sessionFactory) {
        super(Order.class, sessionFactory);
    }

    public List<Order> getAllByUserId(int userId) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);

        criteria
                .select(root)
                .where(builder.equal(root.get("user"), userId))
                .orderBy(builder.desc(root.get("orderDate")));

        return getCurrentSession().createQuery(criteria).getResultList();
    }

    public List<Order> getAllByServicesByUserId(int userId) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();

        CriteriaQuery<Order> criteriaOrder = builder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaOrder.from(Order.class);

        CriteriaQuery<Service> criteriaService = builder.createQuery(Service.class);
        Root<Service> serviceRoot = criteriaService.from(Service.class);

        criteriaService
                .select(serviceRoot.get("id"))
                .where(builder.equal(serviceRoot.get("user"), userId));

        criteriaOrder
                .select(orderRoot)
                .where(orderRoot.get("service")
                        .in(getCurrentSession().createQuery(criteriaService).getResultList()))
                .orderBy(builder.asc(orderRoot.get("orderDate")));

        return getCurrentSession().createQuery(criteriaOrder).getResultList();
    }
}
