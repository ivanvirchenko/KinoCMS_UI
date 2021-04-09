package com.avada.kino.controllers.admin;

import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import com.avada.kino.service.FilesService;
import com.avada.kino.service.MovieService;
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
    private final FilesService fileService;

    @GetMapping
    public String getAllMoviesAdmin(Model model) {
        model.addAttribute("showing", movieService.getShowingMovies());
        model.addAttribute("future", movieService.getFutureMovies());
        return "admin/movies-admin";
    }

    @GetMapping("/{id}")
    public String getMovieById(@PathVariable int id, Model model) {
        Movie fromDb = movieService.getMovieById(id);
        List<MovieType> types = movieService.getTypes();

        model.addAttribute("movie_from_db", fromDb);
        model.addAttribute("all_types", types);
        return "admin/movie-admin";
    }

    @GetMapping("/add")
    public String addMovieGet() {
        return "admin/add_movie_admin";
    }

    @PostMapping("/add")
    public String addMoviePost(
            @RequestParam("file")MultipartFile file,
            @RequestParam("files") MultipartFile[] gallery,
            Movie movie) {

        movieService.save(movie, file, gallery);
        return "redirect:/admin/movies";
    }

    @PostMapping("/update")
    public String updateMovie(
            @RequestParam("main_image") MultipartFile mainImage,
            @RequestParam("image_gallery") MultipartFile[] gallery,
            Movie movie
    ) {
        movieService.update(movie, mainImage, gallery);
        return "redirect:/admin/movies/" + movie.getId();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("movie_id") int id) {
        movieService.delete(id);
        return "redirect:/admin/movies";
    }

    @PostMapping("/file/delete")
    public String deleteFile(@RequestParam("main_image") String imageName, @RequestParam("movie_id") int movieId) {
        fileService.deleteFile(imageName, MOVIE_UPLOAD_DIR);
        return "redirect:/admin/movies/" + movieId;
    }

    @ModelAttribute
    private Movie getNewMovie() {
        return new Movie();
    }
}
