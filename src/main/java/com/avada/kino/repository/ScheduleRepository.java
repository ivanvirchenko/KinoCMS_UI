package com.avada.kino.repository;

import com.avada.kino.models.Schedule;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {
    private final SessionFactory factory;

    public List<Schedule> getByCinema(int cinemaId) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            List<Schedule> list = session.createQuery(
                    "select s from Schedule s left join fetch s.sessions where s.cinema.id = :cinemaId", Schedule.class
            ).setParameter("cinemaId", cinemaId).getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    public Schedule getByDate(LocalDate date) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Schedule list = session.createQuery(
                    "select s from Schedule s left join fetch s.sessions where s.localDate = :date", Schedule.class
            ).setParameter("date", date).getSingleResult();
            session.getTransaction().commit();
            return list;
        }
    }

}
