package com.avada.kino.service;

import com.avada.kino.models.Movie;
import com.avada.kino.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository repository;

    public List<Movie> getMoviesByInShow(boolean inShow) {
        return repository.getByShow(inShow);
    }

    public Movie getMovieById(int id) {
        return repository.getById(id);
    }
}
