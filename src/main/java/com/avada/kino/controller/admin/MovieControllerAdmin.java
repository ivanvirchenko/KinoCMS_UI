package com.avada.kino.controller.admin;

import com.avada.kino.dto.MovieDto;
import com.avada.kino.mapper.MovieMapper;
import com.avada.kino.models.Movie;
import com.avada.kino.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("admin/movies")
@RequiredArgsConstructor
public class MovieControllerAdmin {
    private final MovieService movieService;
    private final MovieMapper mapper;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("movie_current", movieService.getCurrent());
        model.addAttribute("movie_future", movieService.getFuture());
        return "/admin/movies-admin";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        MovieDto dto = mapper.toDto(movieService.getById(id));
        model.addAttribute("movie", dto);
        model.addAttribute("all_types", movieService.getAllTypes());
        return "/admin/movies-admin-concrete";
    }

    @GetMapping("/add")
    public String addMovie(Model model) {
        MovieDto dto = mapper.toDto(new Movie());
        model.addAttribute("new_movie", dto);
        model.addAttribute("all_types", movieService.getAllTypes());
        return "/admin/movies-admin-add";
    }

    @PostMapping("/add")
    public String save(
            MovieDto movieDto,
            @RequestParam("image-input") MultipartFile file,
            @RequestParam("image-pick-input-multiple") MultipartFile[] files
    ) {
        Movie movie = mapper.toMovie(movieDto, movieService.getAllTypes());
        movieService.saveWithFiles(movie, file, files);
        return "redirect:/admin/movies";
    }

    @PostMapping("/update")
    public String update(
            MovieDto movieDto,
            @RequestParam("image-input") MultipartFile file,
            @RequestParam("image-pick-input-multiple") MultipartFile[] files
    ) {
        Movie movie = mapper.toMovie(movieDto, movieService.getAllTypes());
        movieService.updateWithFiles(movie, file, files);
        return "redirect:/admin/movies/" + movie.getId();
    }

    @GetMapping(value = "/gallery/delete", params = {"movie_id", "image_name"})
    public String deleteFromGallery(@RequestParam("movie_id") int movieId, @RequestParam("image_name") String imageName) {
        movieService.deleteFromGallery(movieId, imageName);
        return "redirect:/admin/movies/" + movieId;
    }

    @PostMapping("/image/delete")
    public String deleteMainImage(@RequestParam("movie_id") int movieId, @RequestParam("image_name") String imageName) {
        movieService.deleteMainImage(movieId, imageName);
        return "redirect:/admin/movies/" + movieId;
    }
}
