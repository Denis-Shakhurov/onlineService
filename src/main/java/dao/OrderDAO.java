package dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Order;
import model.Service;
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
        criteria.select(root).where(builder.equal(root.get("user"), userId));
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
                .where(builder.equal(serviceRoot.get("userId"), userId));

        criteriaOrder
                .select(orderRoot)
                .where(orderRoot
                        .get("serviceId")
                        .in(getCurrentSession().createQuery(criteriaService).getResultList()));

        return getCurrentSession().createQuery(criteriaOrder).getResultList();
    }
}
