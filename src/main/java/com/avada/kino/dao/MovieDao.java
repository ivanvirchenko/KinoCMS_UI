package com.avada.kino.dao;

import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieDao implements Dao<Movie>{
    private final SessionFactory sessionFactory;

    @Override
    public void save(Movie movie) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(movie);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Movie> result = session.createQuery(
                    "select m from Movie m left join fetch m.gallery", Movie.class
            ).getResultList();

            result = session.createQuery(
                    "select m from Movie m left join fetch m.types where m in :movies", Movie.class
            ).setParameter("movies", result).getResultList();
            session.getTransaction().commit();
            return result;
        }
    }

    public List<Movie> getCurrent() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Movie> result = session.createQuery(
                    "select m from Movie m where :date between m.startDate and m.endDate", Movie.class
            ).setParameter("date", LocalDate.now()).getResultList();
            session.getTransaction().commit();
            return result;
        }
    }

    public List<Movie> getFuture() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Movie> result = session.createQuery(
                    "select m from Movie m where m.startDate > :date", Movie.class
            ).setParameter("date", LocalDate.now()).getResultList();
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public Movie getById(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Movie result = session.createQuery(
                    "select m from Movie m left join fetch m.gallery where m.id = :id", Movie.class
            ).setParameter("id", id).getSingleResult();

            result = session.createQuery(
                    "select m from Movie m left join fetch m.types where m = :movie", Movie.class
            ).setParameter("movie", result).getSingleResult();

            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public void update(Movie movie) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(movie);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Movie movie = session.get(Movie.class, id);
            session.delete(movie);
            session.getTransaction().commit();
        }
    }

    public List<MovieType> getAllTypes() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<MovieType> types = session.createQuery(
                    "select m from MovieType m", MovieType.class
            ).getResultList();
            session.getTransaction().commit();
            return types;
        }
    }
}
