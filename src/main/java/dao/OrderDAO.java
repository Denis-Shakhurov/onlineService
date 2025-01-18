package dao;

import model.Order;
import org.hibernate.SessionFactory;

public class OrderDAO extends BaseDAO<Order> {

    public OrderDAO(SessionFactory sessionFactory) {
        super(Order.class, sessionFactory);
    }
}
