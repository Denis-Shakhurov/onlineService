package repository;

import dao.OrderDAO;
import model.Order;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository extends BaseRepository {
    private final OrderDAO orderDAO = new OrderDAO(sessionFactory);

    public List<Order> findAllByUserId(int userId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            List<Order> orders = new ArrayList<>();
            session.beginTransaction();
            orders.addAll(orderDAO.getAllByUserId(userId));
            session.getTransaction().commit();
            return orders;
        }
    }

    public Optional<Order> findById(int orderId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Order order;
            session.beginTransaction();
            order = orderDAO.getById(orderId);
            session.getTransaction().commit();
            return Optional.ofNullable(order);
        }
    }

    public Order save(Order orderForSave) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Order order;
            session.beginTransaction();
            order = orderDAO.create(orderForSave);
            session.getTransaction().commit();
            return order;
        }
    }

    public List<Order> getAllByServicesByUserId(int userId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            List<Order> orders = new ArrayList<>();
            session.beginTransaction();
            orders.addAll(orderDAO.getAllByServicesByUserId(userId));
            session.getTransaction().commit();
            return orders;
        }
    }
}
