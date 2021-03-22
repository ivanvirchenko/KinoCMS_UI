package com.avada.kino.repository;

import com.avada.kino.models.Movie;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieRepository {

    private final SessionFactory sessionFactory;

    public List<Movie> getByShow(boolean inShow) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            List<Movie> list = session.createQuery(
                    "from Movie m where m.inShow = " + inShow, Movie.class
            ).getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    public Movie getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Movie movie;
            session.getTransaction().begin();
            movie = session.createQuery(
                    "select m from Movie m join fetch m.gallery where m.id = " + id, Movie.class
            ).getSingleResult();

            session.getTransaction().commit();

            return movie;
        }
    }
}
