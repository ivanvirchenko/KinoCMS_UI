package com.avada.kino.dao;

import com.avada.kino.models.Cinema;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CinemaDao implements Dao<Cinema> {

    private final SessionFactory sessionFactory;

    @Override
    public void save(Cinema cinema) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(cinema);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Cinema> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Cinema> result = session.createQuery(
                    "select c from Cinema c", Cinema.class
            ).getResultList();

            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public Cinema getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Cinema cinema = session.createQuery(
                    "select c from Cinema c left join fetch c.gallery where c.id = :id", Cinema.class
            ).setParameter("id", id).getSingleResult();

            cinema = session.createQuery(
                    "select c from Cinema c left join fetch c.hallsList where c = :cinema", Cinema.class
            ).setParameter("cinema", cinema).getSingleResult();

            session.getTransaction().commit();
            return cinema;
        }
    }

    @Override
    public void update(Cinema cinema) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(cinema);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Cinema cinema = session.get(Cinema.class, id);
            session.delete(cinema);
            session.getTransaction().commit();
        }
    }
}
