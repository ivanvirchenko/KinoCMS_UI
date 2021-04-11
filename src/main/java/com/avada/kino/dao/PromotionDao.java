package com.avada.kino.dao;

import com.avada.kino.models.Promotion;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PromotionDao implements Dao<Promotion> {

    private final SessionFactory sessionFactory;

    @Override
    public void save(Promotion promotion) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(promotion);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Promotion> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Promotion> result = session.createQuery(
                    "select p from Promotion p left join fetch p.gallery", Promotion.class
            ).getResultList();
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public Promotion getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Promotion result = session.createQuery(
                    "select p from Promotion p left join fetch p.gallery where p.id = :id", Promotion.class
            ).setParameter("id", id).getSingleResult();
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public void update(Promotion promotion) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(promotion);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Promotion promotion = session.get(Promotion.class, id);
            session.delete(promotion);
            session.getTransaction().commit();
        }
    }
}
