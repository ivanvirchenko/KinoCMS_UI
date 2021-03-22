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

    public News getById(int id) {
        return repository.getById(id);
    }
}
