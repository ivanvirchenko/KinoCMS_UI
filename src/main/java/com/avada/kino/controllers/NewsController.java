package com.avada.kino.controllers;

import com.avada.kino.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService service;

    @GetMapping
    public String newsAll(Model model) {
        model.addAttribute("news_all", service.getAll());
        return "news_all";
    }

    @GetMapping("/{id}")
    public String news(@PathVariable int id, Model model) {
        model.addAttribute("theNews", service.getById(id));
        return "news";
    }
}
