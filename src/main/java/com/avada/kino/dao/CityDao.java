package com.avada.kino.dao;

import com.avada.kino.models.City;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CityDao implements Dao<City>{
    private final SessionFactory sessionFactory;

    @Override
    public void save(City city) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(city);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<City> getAll() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<City> result =  session.createQuery(
                    "select c from City c", City.class
            ).getResultList();
            session.getTransaction().commit();

            return result;
        }
    }

    @Override
    public City getById(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            City city = session.get(City.class, id);
            session.getTransaction().commit();
            return city;
        }
    }

    @Override
    public void update(City city) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(city);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from City c where c.id = :id", City.class).setParameter("id", id);
            session.getTransaction().commit();
        }
    }
}
