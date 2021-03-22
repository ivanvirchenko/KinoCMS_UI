package com.avada.kino.repository;

import com.avada.kino.models.Cinema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CinemaRepository {

    private final SessionFactory sessionFactory;

    public List<Cinema> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Cinema> list = session.createQuery(
                    "select c from Cinema c", Cinema.class
            ).getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    public Cinema getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Cinema cinema = session.createQuery(
                    "select distinct c from Cinema c left join fetch c.hallsList where c.id = :id",
                    Cinema.class
            ).setParameter("id", id)
                    .getSingleResult();

            cinema = session.createQuery(
                    "select distinct c from Cinema c left join fetch c.gallery where c = :cinema",
                    Cinema.class
            ).setParameter("cinema", cinema).getSingleResult();
            session.getTransaction().commit();
            return cinema;
        }
    }

}
