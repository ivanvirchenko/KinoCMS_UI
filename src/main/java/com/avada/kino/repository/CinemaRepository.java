package com.avada.kino.repository;

import com.avada.kino.models.Cinema;
import com.avada.kino.models.Hall;
import com.avada.kino.models.Image;
import com.avada.kino.models.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CinemaRepository {

    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    public List<Cinema> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Cinema> list = session.createQuery(
                    "select c from Cinema c", Cinema.class
            ).getResultList();
            session.getTransaction().commit();
            return list;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

//    private Cinema get(int id) {
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            Cinema cinema = session.createQuery(
//                    "select distinct c from Cinema c left join fetch c.hallsList where c.id = :id",
//                    Cinema.class
//            ).setParameter("id", id)
//                    .getSingleResult();
//
//            cinema = session.createQuery(
//                    "select distinct c from Cinema c left join fetch c.gallery where c = :cinema",
//                    Cinema.class
//            ).setParameter("cinema", cinema).getSingleResult();
//
//            cinema = session.createQuery(
//                    "select distinct c from Cinema c left join fetch c.schedules` where c = :cinema",
//                    Cinema.class
//            ).setParameter("cinema", cinema).getSingleResult();
//
//            session.getTransaction().commit();
//            return cinema;
//        } catch (EmptyResultDataAccessException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public Cinema getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Cinema cinema = session.createQuery(
                    "select c from Cinema c " +
                            "left join fetch c.hallsList " +
                            "left join c.schedules " +
                            "left join c.gallery " +
                            "where c.id = :id"
                    ,Cinema.class
            ).setParameter("id", id).getSingleResult();

            session.getTransaction().commit();
            return cinema;
        }
    }

    public void save(Cinema cinema) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(cinema);
            session.getTransaction().commit();
        }
    }

    public void update(Cinema cinema) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(cinema);
            session.getTransaction().commit();
        }
    }

    public void delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery(
                    "delete from Cinema c where c.id = :id"
            ).setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        }
    }

    public void addHall(int cinemaId, Hall hall) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Cinema cinema = getById(cinemaId);
            cinema.add(hall);
            session.save(hall);
            session.getTransaction().commit();
        }
    }

    public void addSchedule(int cinemaId, Schedule schedule) {
        Cinema cinema = getById(cinemaId);
        cinema.add(schedule);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(schedule);
            session.getTransaction().commit();
        }
    }

}
