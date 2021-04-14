package com.avada.kino.dao;

import com.avada.kino.models.News;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NewsDao implements Dao<News>{
    private final SessionFactory sessionFactory;

    @Override
    public void save(News news) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(news);
            session.getTransaction().commit();
        }
    }

    public List<News> getAll() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<News> result =  session.createQuery(
                    "select n from News n", News.class
            ).getResultList();
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public News getById(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            News result = session.createQuery(
                    "select n from News n left join fetch n.gallery where n.id = :id", News.class
            ).setParameter("id", id).getSingleResult();
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public void update(News news) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(news);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            News news = session.get(News.class, id);
            session.delete(news);
            session.getTransaction().commit();
        }
    }
}
