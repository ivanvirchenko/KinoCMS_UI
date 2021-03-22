package com.avada.kino.repository;

import com.avada.kino.models.News;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

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

    public News getById(int id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            News news = session.get(News.class, id);
            session.getTransaction().commit();
            return news;
        }
    }
}
