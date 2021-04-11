package com.avada.kino.service;

import com.avada.kino.models.News;
import com.avada.kino.dao.NewsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService implements DaoService<News> {

    private final NewsDao dao;

    @Override
    public void save(News news) {
        dao.save(news);
    }

    @Override
    public List<News> getAll() {
        return dao.getAll();
    }

    @Override
    public News getById(int id) {
        return dao.getById(id);
    }

    @Override
    public void update(News news) {
        dao.update(news);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}
