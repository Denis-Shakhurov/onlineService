package dao;

import model.User;
import org.hibernate.SessionFactory;

public class UserDAO extends BaseDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }
}
