package com.avada.kino.repository;

import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import com.avada.kino.models.Seo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovieRepository {

    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

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

    public void save(Movie movie) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(movie);
            session.getTransaction().commit();
        }
    }

    public void update(Movie movie) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            var movieFromDb = session.createQuery(
                    "select m from Movie m left join fetch m.gallery where m.id = :id",
                    Movie.class
            ).setParameter("id", movie.getId()).getSingleResult();

            movieFromDb = session.createQuery(
                    "select m from Movie m left join fetch m.types where m = :movie", Movie.class
            ).setParameter("movie", movieFromDb).getSingleResult();

            Optional.ofNullable(movie.getName()).ifPresent(movieFromDb::setName);
            Optional.of(movie.getStartDate()).ifPresent(movieFromDb::setStartDate);
            Optional.of(movie.getEndDate()).ifPresent(movieFromDb::setEndDate);
            Optional.ofNullable(movie.getVideoLink()).ifPresent(movieFromDb::setVideoLink);
            Optional.ofNullable(movie.getTypes()).ifPresent(movieFromDb::setTypes);


            Optional.ofNullable(movie.getSeo()).ifPresent(movieFromDb::setSeo);

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

    public Integer getTotalMoviesCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from movie", Integer.class
        );
    }

    public Integer getCurrentMoviesCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from movie where date(now()) between start_date and end_date"
                , Integer.class
        );
    }

    public Integer getFutureMoviesCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from movie where start_date > date(now())", Integer.class
        );
    }
}
