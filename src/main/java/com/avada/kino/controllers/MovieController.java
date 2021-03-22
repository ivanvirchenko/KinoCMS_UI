package com.avada.kino.controllers;

import com.avada.kino.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService service;

    @GetMapping("/{id}")
    public String getMovieById(@PathVariable int id, Model model) {
        model.addAttribute("movie", service.getMovieById(id));
        return "movie";
    }

    @GetMapping("/poster")
    public String getPoster(Model model) {
        model.addAttribute("movies", service.getMoviesByInShow(true));
        return "poster";
    }

    @GetMapping("/soon")
    public String getSoon(Model model) {
        model.addAttribute("movies", service.getMoviesByInShow(false));
        return "soon";
    }
}
