package com.avada.kino.repository;

import com.avada.kino.models.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final SessionFactory sessionFactory;

    public void save(User user) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    public List<User> getAll() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> list = session.createQuery("select u from User u", User.class).getResultList();
            session.getTransaction().commit();
            return list;
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public User getById(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.find(User.class, id);
            session.getTransaction().commit();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    public void update(User user) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    public void delete(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User u where u.id = :id")
                    .setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
