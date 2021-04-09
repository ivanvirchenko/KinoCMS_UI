package com.avada.kino.controllers.admin;

import com.avada.kino.models.Image;
import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import com.avada.kino.service.MovieService;
import com.avada.kino.service.FilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.avada.kino.util.UploadPaths.MOVIE_UPLOAD_DIR;

@Controller
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class AdminMoviesController {

    private final MovieService movieService;
    private final FilesService uploadService;

    @GetMapping
    public String getAllMoviesAdmin(Model model) {
        model.addAttribute("showing", movieService.getShowingMovies());
        model.addAttribute("future", movieService.getFutureMovies());
        return "admin/movies-admin";
    }

    @GetMapping("/{id}")
    public String getMovieById(@PathVariable int id, Model model) {
        model.addAttribute("movie_from_db", movieService.getMovieById(id));
        return "admin/movie-admin";
    }

    @GetMapping("/add")
    public String addMovieGet(Model model) {
        model.addAttribute("theTypes", movieService.getTypes());
        return "admin/add_movie_admin";
    }

    @PostMapping("/add")
    public String addMoviePost(
            @RequestParam("file")MultipartFile file,
            @RequestParam("files") MultipartFile[] gallery,
            Movie movie) {

        movieService.save(movie);
        return "redirect:/admin/movies";
    }

    @ModelAttribute
    private Movie getNewMovie() {
        Movie movie = new Movie();
        return movie;
    }

    @ModelAttribute(name = "all_types")
    private List<MovieType> movieTypes() {
        return movieService.getTypes();
    }
}
