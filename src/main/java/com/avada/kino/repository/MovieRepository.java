package com.avada.kino.repository;

import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovieRepository {

    private final SessionFactory sessionFactory;

    public List<Movie> getShowingMovies() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Movie> movies = session.createQuery(
                    "select m from Movie m where :date between m.startDate and m.endDate", Movie.class
            ).setParameter("date", new Date())
                    .getResultList();

            session.getTransaction().commit();
            return movies;

        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Movie> getFutureMovies() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Movie> movies = session.createQuery(
                    "select m from Movie m where m.startDate > :date", Movie.class
            ).setParameter("date", new Date())
                    .getResultList();

            session.getTransaction().commit();
            return movies;

        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Movie getById(int id) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Movie movie = session.createQuery(
                    "select m from Movie m " +
                            "left join fetch m.gallery " +
                            "where m.id = :id", Movie.class
            ).setParameter("id", id).getSingleResult();

            movie = session.createQuery(
                    "select m from Movie m " +
                            "left join fetch m.types where m = :movie", Movie.class
            ).setParameter("movie", movie).getSingleResult();

            session.getTransaction().commit();
            return movie;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Movie> getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Movie> movies = session.createQuery(
                    "select m from Movie m where m.name like concat('%', :name, '%') "
                    , Movie.class
            ).setParameter("name", name).getResultList();

            session.getTransaction().commit();
            return movies;
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(Movie movie) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(movie);
            session.getTransaction().commit();
        }
    }

    public void update(int id, Movie movieNew) {
        Movie fromDb;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            fromDb = session.createQuery(
                    "select m from Movie m join fetch m.gallery " +
                            "where m.id = :id", Movie.class
                    ).setParameter("id", id).getSingleResult();

            Optional.ofNullable(movieNew.getName()).ifPresent(fromDb::setName);
            Optional.ofNullable(movieNew.getStartDate()).ifPresent(fromDb::setStartDate);
            Optional.ofNullable(movieNew.getEndDate()).ifPresent(fromDb::setEndDate);
            Optional.ofNullable(movieNew.getDescription()).ifPresent(fromDb::setDescription);
            Optional.ofNullable(movieNew.getGallery()).ifPresent(fromDb::setGallery);
            Optional.ofNullable(movieNew.getVideoLink()).ifPresent(fromDb::setVideoLink);
            Optional.ofNullable(movieNew.getTypes()).ifPresent(fromDb::setTypes);
            Optional.ofNullable(movieNew.getSeo()).ifPresent(fromDb::setSeo);

            session.getTransaction().commit();
        }
    }

    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery(
                    "delete from Movie m where m.id = :id"
            ).setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        }
    }

    public List<MovieType> getTypes() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<MovieType> types = session.createQuery(
                    "select mt from MovieType mt", MovieType.class
            ).getResultList();
            session.getTransaction().commit();
            return types;
        }
    }
}
