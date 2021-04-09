package com.avada.kino.repository;

import com.avada.kino.models.News;
import com.avada.kino.models.Seo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsRepository {
    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    public List<News> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<News> list = session.createQuery("select n from News n", News.class).getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    public List<News> getEnabled(boolean enable) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<News> list = session.createQuery(
                    "select n from News n where n.enabled = :enabled"
                    , News.class
            ).setParameter("enabled", enable).getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    public News getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            News news = session.createQuery(
                    "select n from News n left join fetch n.gallery where n.id = :id", News.class
            ).setParameter("id", id).getSingleResult();

            session.getTransaction().commit();
            return news;
        }
    }

    public void save(News news) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(news);
            session.getTransaction().commit();
        }
    }

    public void update(News news) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            News newsFromDb = session.createQuery(
                    "select n from News n left join fetch n.gallery where n.id = :id", News.class
            ).setParameter("id", news.getId()).getSingleResult();

            Optional.ofNullable(news.getImage()).ifPresent(newsFromDb::setImage);
            Optional.ofNullable(news.getGallery()).ifPresent(images -> {
                images.forEach(newsFromDb::addToGallery);
            });
            Optional.ofNullable(news.getName()).ifPresent(newsFromDb::setName);
            Optional.ofNullable(news.getDate()).ifPresent(newsFromDb::setDate);
            Optional.of(news.isEnabled()).ifPresent(newsFromDb::setEnabled);
            Optional.ofNullable(news.getDescription()).ifPresent(newsFromDb::setDescription);
            Optional.ofNullable(news.getVideoLink()).ifPresent(videoLink -> {
                if (!news.getVideoLink().isBlank()) {
                    newsFromDb.setVideoLink(videoLink);
                }
            });
            Optional.ofNullable(news.getSeo()).ifPresent(seo -> {
                if (!seo.getUrl().isBlank()) {
                    newsFromDb.getSeo().setUrl(seo.getUrl());
                }
                if (!seo.getTitle().isBlank()) {
                    newsFromDb.getSeo().setTitle(seo.getTitle());
                }
                if (!seo.getKeyWords().isBlank()) {
                    newsFromDb.getSeo().setKeyWords(seo.getKeyWords());
                }
                if (!seo.getDescription().isBlank()) {
                    newsFromDb.getSeo().setDescription(seo.getDescription());
                }
            });

            session.getTransaction().commit();
        }
    }

    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            News news = session.get(News.class, id);
            session.delete(news);
            session.getTransaction().commit();
        }
    }

    public Integer getTotalNewsCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from news", Integer.class
        );
    }

    public Integer getEnabledNewsCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from news where enabled = true", Integer.class
        );
    }

    public Integer getDisabledNewsCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from news where enabled = false", Integer.class
        );
    }
}
