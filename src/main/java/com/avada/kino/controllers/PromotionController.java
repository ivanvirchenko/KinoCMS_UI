package com.avada.kino.controllers;

import com.avada.kino.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService service;

    @GetMapping
    public String promotions(Model model) {
        model.addAttribute("promotions", service.getEnabled(true));
        return "promotions";
    }

    @GetMapping("/{id}")
    public String promotion(@PathVariable int id, Model model) {
        model.addAttribute("promotion", service.getById(id));
        return "promotion";
    }
}
