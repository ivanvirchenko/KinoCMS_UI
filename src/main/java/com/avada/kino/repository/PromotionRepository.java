package com.avada.kino.repository;

import com.avada.kino.models.Promotion;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PromotionRepository {
    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

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

            Promotion promotion = session.createQuery(
                    "select p from Promotion p left join fetch p.gallery where p.id = :id", Promotion.class
            ).setParameter("id", id).getSingleResult();

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
            Promotion promotionFromDB = session.createQuery(
                    "select p from Promotion p left join fetch p.gallery where p.id = : id", Promotion.class
            ).setParameter("id", promotion.getId()).getSingleResult();

            Optional.ofNullable(promotion.getImage()).ifPresent(promotionFromDB::setImage);
            Optional.ofNullable(promotion.getName()).ifPresent(promotionFromDB::setName);
            Optional.ofNullable(promotion.getDate()).ifPresent(promotionFromDB::setDate);
            Optional.of(promotion.isEnabled()).ifPresent(promotionFromDB::setEnabled);
            Optional.ofNullable(promotion.getDescription()).ifPresent(promotionFromDB::setDescription);
            Optional.ofNullable(promotion.getVideoLink()).ifPresent(promotionFromDB::setVideoLink);
            Optional.ofNullable(promotion.getGallery()).ifPresent(images -> {
                images.forEach(promotionFromDB::addToGallery);
            });
            Optional.ofNullable(promotion.getSeo().getUrl())
                    .ifPresent(url ->promotionFromDB.getSeo().setUrl(url));
            Optional.ofNullable(promotion.getSeo().getTitle())
                    .ifPresent(title ->promotionFromDB.getSeo().setTitle(title));
            Optional.ofNullable(promotion.getSeo().getTitle())
                    .ifPresent(title ->promotionFromDB.getSeo().setTitle(title));
            Optional.ofNullable(promotion.getSeo().getKeyWords())
                    .ifPresent(keys ->promotionFromDB.getSeo().setKeyWords(keys));
            Optional.ofNullable(promotion.getSeo().getDescription())
                    .ifPresent(description ->promotionFromDB.getSeo().setDescription(description));

            session.getTransaction().commit();
        }
    }

    public void delete(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Promotion promotion = session.get(Promotion.class, id);
            session.delete(promotion);
            session.getTransaction().commit();
        }
    }

    public Integer getTotalPromCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from promotion", Integer.class
        );
    }

    public Integer getEnabledPromCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from promotion where enabled = true", Integer.class
        );
    }

    public Integer getDisabledPromCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from promotion where enabled = false", Integer.class
        );
    }
}
