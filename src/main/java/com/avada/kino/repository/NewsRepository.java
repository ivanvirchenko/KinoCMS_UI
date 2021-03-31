package com.avada.kino.repository;

import com.avada.kino.models.News;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NewsRepository {
    private final SessionFactory sessionFactory;

    public List<News> getAll() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<News> list = session.createQuery("select n from News n", News.class).getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    public List<News> getEnabled(boolean enable) {
        try(Session session = sessionFactory.openSession()) {
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
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            News news = session.get(News.class, id);
            session.getTransaction().commit();
            return news;
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
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
            session.update(news);
            session.getTransaction().commit();
        }
    }

    public void delete(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from News n where n.id = :id")
                    .setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
