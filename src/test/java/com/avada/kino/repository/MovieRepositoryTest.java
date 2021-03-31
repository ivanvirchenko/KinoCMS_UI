package com.avada.kino.repository;

import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieRepositoryTest {


    @Autowired
    private MovieRepository repository;
    private Movie movieNow = new Movie(
            "current", null, null, null, null,
            null,
            LocalDate.now(),
            LocalDate.now().plusDays(14)

    );
    private Movie movieFuture = new Movie(
            "future", null, null, null, null,
            null,
            LocalDate.now().plusDays(14),
            LocalDate.now().plusDays(28)

    );

    @Test
    @Order(1)
    void shouldSaveMovie() {
        assertDoesNotThrow(() -> repository.save(movieNow));
        assertDoesNotThrow(() -> repository.save(movieFuture));
    }

    @Test
    @Order(2)
    void shouldReturnOnlyShowingMovies() {
        List<Movie> movie = repository.getShowingMovies();
        for (Movie m : movie) {
            assertEquals(movieNow.getName(), m.getName());
        }
    }

    @Test
    @Order(3)
    void shouldReturnOnlyFutureMovies() {
        List<Movie> movie = repository.getFutureMovies();
        for (Movie m : movie) {
            assertEquals(movieFuture.getName(), m.getName());
        }
    }

    @Test
    @Order(4)
    void shouldReturnCorrectMovieById() {
        assertEquals(movieNow.getName(), repository.getById(1).getName());
    }

    @Test
    @Order(5)
    void shouldReturnMoviesByName() {
        List<Movie> movies = repository.getByName(movieNow.getName());
        for (Movie m : movies) {
            assertEquals(movieNow.getName(), m.getName());
        }
    }

    @Test
    @Order(7)
    void shouldDeleteMovieById() {
        repository.delete(2);

        assertNull(repository.getById(2));
    }
}
