package com.avada.kino.repository;

import com.avada.kino.models.Promotion;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PromotionRepository {
    private final SessionFactory sessionFactory;

    public List<Promotion> getAll() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Promotion> list = session.createQuery("select p from Promotion p", Promotion.class).getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    public List<Promotion> getEnabled(boolean enabled) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Promotion> list = session.createQuery(
                    "select p from Promotion p where p.enabled = :enabled"
                    , Promotion.class
            ).setParameter("enabled", enabled)
                    .getResultList();

            session.getTransaction().commit();
            return list;
        }
    }

    public Promotion getById(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Promotion promotion = session.get(Promotion.class, id);
            session.getTransaction().commit();
            return promotion;
        }
    }

    public void save(Promotion promotion) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(promotion);
            session.getTransaction().commit();
        }
    }

    public void update(Promotion promotion) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(promotion);
            session.getTransaction().commit();
        }
    }

    public void delete(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery(
                    "delete from Promotion p where p.id = :id"
            ).setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
