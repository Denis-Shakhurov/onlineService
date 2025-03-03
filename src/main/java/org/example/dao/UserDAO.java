package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.model.User;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDAO extends BaseDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

    public boolean existsByEmail(String email) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(builder.equal(root.get("email"), email));
        return getCurrentSession().createQuery(criteria).uniqueResult() != null;
    }

    public User findByEmail(String email) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(builder.equal(root.get("email"), email));
        return getCurrentSession().createQuery(criteria).uniqueResult();
    }

    public List<User> findAllByRole(String role) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(builder.equal(root.get("role"), role));
        return getCurrentSession().createQuery(criteria).list();
    }
}
