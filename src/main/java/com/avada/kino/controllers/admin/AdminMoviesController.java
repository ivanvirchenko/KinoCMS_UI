package com.avada.kino.controllers.admin;

import com.avada.kino.models.Movie;
import com.avada.kino.models.Seo;
import com.avada.kino.service.MovieService;
import com.avada.kino.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class AdminMoviesController {

    private final MovieService movieService;
    private final UploadService uploadService;

    @GetMapping
    public String getMoviesAdmin(Model model) {
        model.addAttribute("showing", movieService.getShowingMovies());
        model.addAttribute("future", movieService.getFutureMovies());
        return "admin/movies-admin";
    }

    @GetMapping("/{id}")
    public String getMoviesAdmin(@PathVariable int id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        model.addAttribute("allTypes", movieService.getTypes());
        return "admin/movie-admin";
    }

    @GetMapping("/add")
    public String addMoviePage(Model model) {
        model.addAttribute("newMovie", getNewMovie());
        model.addAttribute("theTypes", movieService.getTypes());
        return "admin/add_movie_admin";
    }

    @PostMapping("/add")
    public String addMovie(@RequestParam("file")MultipartFile file, Movie movie) {
        String filePath = uploadService.upload(file);
        movie.setImgUrl(filePath);
        movieService.save(movie);
        return "redirect:/admin/movies";
    }

    @PostMapping("/update")
    public String updateMovie(
            Movie movie, @RequestParam int id) {
        movieService.update(id, movie);
        return "redirect:/admin/movies";
    }

    @DeleteMapping("/delete")
    public String deleteMovie(@RequestParam int id) {
        movieService.delete(id);
        return "redirect:/admin/movies";
    }

    @PostMapping("/file/delete")
    public String deleteFile(@RequestParam("file")MultipartFile file) {
        uploadService.delete(file.getOriginalFilename());
        return "redirect:/admin/movies";
    }

    @ModelAttribute
    private Movie getNewMovie() {
        Movie movie = new Movie();
        movie.setSeo(new Seo());
        movie.setGallery(new ArrayList<>());
        movie.setTypes(new ArrayList<>());
        return movie;
    }
}
