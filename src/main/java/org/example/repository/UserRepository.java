package org.example.repository;

import org.example.dao.UserDAO;
import org.example.model.User;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository extends BaseRepository {
    private final UserDAO userDAO = new UserDAO(sessionFactory);

    public List<User> findAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            List<User> users = new ArrayList<>();
            session.beginTransaction();
            users.addAll(userDAO.findAll());
            session.getTransaction().commit();
            return users;
        }
    }

    public List<User> findAllByRole(String role) {
        try (Session session = sessionFactory.getCurrentSession()) {
            List<User> users = new ArrayList<>();
            session.beginTransaction();
            users.addAll(userDAO.findAllByRole(role));
            session.getTransaction().commit();
            return users;
        }
    }

    public Optional<User> findById(Integer id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            User user;
            session.beginTransaction();
            user = userDAO.getById(id);
            session.getTransaction().commit();
            return Optional.ofNullable(user);
        }
    }

    public User save(User userForSave) {
        try (Session session = sessionFactory.getCurrentSession()) {
            User user;
            session.beginTransaction();
            user = userDAO.create(userForSave);
            session.getTransaction().commit();
            return user;
        }
    }

    public User update(User userForUpdate) {
        try (Session session = sessionFactory.getCurrentSession()) {
            User user;
            session.beginTransaction();
            user = userDAO.update(userForUpdate);
            session.getTransaction().commit();
            return user;
        }
    }

    public boolean existsByEmail(String email) {
        try (Session session = sessionFactory.getCurrentSession()) {
            boolean exists;
            session.beginTransaction();
            exists =  userDAO.existsByEmail(email);
            session.getTransaction().commit();
            return exists;
        }
    }

    public Optional<User> findByEmail(String email) {
        try (Session session = sessionFactory.getCurrentSession()) {
            User user;
            session.beginTransaction();
            user = userDAO.findByEmail(email);
            session.getTransaction().commit();
            return Optional.ofNullable(user);
        }
    }
}
