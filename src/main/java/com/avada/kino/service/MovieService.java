package com.avada.kino.service;

import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import com.avada.kino.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository repository;

    public List<Movie> getShowingMovies() {
        return repository.getShowingMovies();
    }

    public List<Movie> getFutureMovies() {
        return repository.getFutureMovies();
    }

    public Movie getMovieById(int id) {
        return repository.getById(id);
    }

    public List<Movie> getByName(String name) {
        return repository.getByName(name);
    }

    public void save(Movie movie) {
        repository.save(movie);
    }

    public void update(int id, Movie movie) {
        repository.update(id, movie);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public List<MovieType> getTypes() {
        return repository.getTypes();
    }
}
