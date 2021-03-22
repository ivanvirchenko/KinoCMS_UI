package com.avada.kino.controllers;

import com.avada.kino.models.Cinema;
import com.avada.kino.service.CinemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/cinema")
public class CinemaController {
    private final CinemaService service;

    @GetMapping("/all")
    public String cinemas(Model model) {
        model.addAttribute("cinema_list", service.getAll());
        return "cinemas_all";
    }

    @GetMapping("/{id}")
    public String cinema(@PathVariable int id, Model model) {
        Cinema cinema = service.getById(id);
        model.addAttribute("cinema", cinema);
        model.addAttribute("hall_list", cinema.getHallsList());

        return "cinema";
    }
}
