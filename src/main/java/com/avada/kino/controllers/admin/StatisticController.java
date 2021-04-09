package com.avada.kino.controllers.admin;

import com.avada.kino.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/statistic")
@RequiredArgsConstructor
public class StatisticController {
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final NewsService newsService;
    private final PromotionService promotionService;
    private final UserService userService;

    @GetMapping
    public String getStatistics(Model model) {

        model.addAttribute("users_count", userService.getUsersCount());

        model.addAttribute("total_movies", movieService.getTotalMoviesCount());
        model.addAttribute("current_movies", movieService.getCurrentMoviesCount());
        model.addAttribute("future_movies", movieService.getFutureMoviesCount());

        model.addAttribute("total_news", newsService.getTotalNewsCount());
        model.addAttribute("enabled_news", newsService.getEnabledNewsCount());
        model.addAttribute("disabled_news", newsService.getDisabledNewsCount());

        model.addAttribute("total_promotions", promotionService.getTotalPromCount());
        model.addAttribute("enabled_promotions", promotionService.getEnabledPromCount());
        model.addAttribute("disabled_promotions", promotionService.getDisabledPromCount());

        return "admin/statistic";
    }
}
