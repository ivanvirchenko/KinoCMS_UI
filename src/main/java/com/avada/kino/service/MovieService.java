package com.avada.kino.service;

import com.avada.kino.models.Movie;
import com.avada.kino.dao.MovieDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService implements DaoService<Movie>{

    private final MovieDao dao;

    @Override
    public void save(Movie movie) {
        dao.save(movie);
    }

    @Override
    public List<Movie> getAll() {
        return dao.getAll();
    }

    @Override
    public Movie getById(int id) {
        return dao.getById(id);
    }

    @Override
    public void update(Movie movie) {
        dao.update(movie);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}
