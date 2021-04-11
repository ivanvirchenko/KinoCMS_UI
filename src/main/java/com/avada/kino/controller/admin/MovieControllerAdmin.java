package com.avada.kino.controller.admin;

import com.avada.kino.models.Movie;
import com.avada.kino.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/movies")
@RequiredArgsConstructor
public class MovieControllerAdmin {
    private final MovieService movieService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("movie_list", movieService.getAll());
        return "/admin/movies";
    }

    @GetMapping("/add")
    public String addMovie(Model model) {
        model.addAttribute("new_movie", new Movie());
        return "/admin/movie_add";
    }
}
