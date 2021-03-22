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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Promotion> list = session.createQuery(
                    "select p from Promotion p ", Promotion.class
            ).getResultList();
            session.getTransaction().commit();
            return list;
        }
    }
}
