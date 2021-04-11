package com.avada.kino.api.admin;

import com.avada.kino.models.Movie;
import com.avada.kino.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/movies")
@RequiredArgsConstructor
public class MovieRestControllerAdmin {
    private final MovieService movieService;

    @GetMapping
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @GetMapping(params = "id")
    public Movie getById(@RequestParam int id) {
        return movieService.getById(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody Movie movie) {
        movieService.save(movie);
    }

    @PutMapping("/update")
    public void update(@RequestBody Movie movie) {
        movieService.update(movie);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam int movie_id) {
        movieService.delete(movie_id);
    }
}
