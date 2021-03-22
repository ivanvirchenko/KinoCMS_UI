package com.avada.kino.controllers;

import com.avada.kino.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService service;

    @GetMapping
    public String promotions(Model model) {
        model.addAttribute("promotions", service.getAll());
        return "promotions";
    }
}
