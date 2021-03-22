package com.avada.kino.controllers;

import com.avada.kino.repository.MovieRepository;
import com.avada.kino.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final MovieService service;
    @GetMapping
    public String home(Model model) {
        model.addAttribute("movies_in_show", service.getMoviesByInShow(true));
        model.addAttribute("future_movies", service.getMoviesByInShow(false));
        return "index";
    }
}
