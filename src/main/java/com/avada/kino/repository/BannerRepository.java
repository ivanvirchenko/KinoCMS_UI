package com.avada.kino.repository;

import com.avada.kino.models.Banner;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BannerRepository {
    private final SessionFactory sessionFactory;

    public void save(Banner banner) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(banner);
            session.getTransaction().commit();
        }
    }

    public Banner getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Banner banner = session.createQuery(
                    "select b from Banner b left join fetch b.images where b.id = :id", Banner.class
            ).setParameter("id", id).getSingleResult();

            session.getTransaction().commit();
            return banner;
        }
    }

    public List<Banner> getAll() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Banner> banners = session.createQuery("select b from Banner b", Banner.class).getResultList();
            session.getTransaction().commit();
            return banners;
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Banner banner) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Banner bannerFromDB = session.createQuery(
                    "select b from Banner b left join b.images where b.id = :id", Banner.class
            ).setParameter("id", banner.getId()).getSingleResult();

            Optional.ofNullable(banner.getPageName()).ifPresent(bannerFromDB::setPageName);
            Optional.ofNullable(banner.getImages()).ifPresent(images -> {
                images.forEach(bannerFromDB::addImage);
            });
        }
    }

    public void delete(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Banner banner = session.get(Banner.class, id);
            session.delete(banner);
            session.getTransaction().commit();
        }
    }
}
