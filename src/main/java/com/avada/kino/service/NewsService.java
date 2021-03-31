package com.avada.kino.service;

import com.avada.kino.models.News;
import com.avada.kino.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository repository;

    public List<News> getAll() {
        return repository.getAll();
    }

    public List<News> getEnabled(boolean enabled) {
        return repository.getEnabled(enabled);
    }

    public News getById(int id) {
        return repository.getById(id);
    }

    public void save(News news) {
        repository.save(news);
    }

    public void update(News news) {
        repository.update(news);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
