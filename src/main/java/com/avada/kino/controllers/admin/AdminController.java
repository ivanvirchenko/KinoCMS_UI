package com.avada.kino.controllers.admin;

import com.avada.kino.models.Movie;
import com.avada.kino.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MovieService movieService;

    @GetMapping
    public String getAdmin() {
        return "admin/index-admin";
    }

    @GetMapping("/movies")
    public String getMoviesAdmin(Model model) {
        model.addAttribute("showing", movieService.getShowingMovies());
        model.addAttribute("future", movieService.getFutureMovies());
        return "admin/movies-admin";
    }

    @GetMapping("/movies/{id}")
    public String getMoviesAdmin(@PathVariable int id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        model.addAttribute("types", movieService.getTypes());
        return "admin/movie-admin";
    }

    @PostMapping("/movies/add")
    public String updateMovie(@RequestParam int id, Movie movie) {
        movieService.update(id, movie);
        return "redirect:/admin/movies";
    }

    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        return "redirect:/admin/movies/";
    }
}
