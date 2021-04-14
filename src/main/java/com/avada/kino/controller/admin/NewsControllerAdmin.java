package com.avada.kino.controller.admin;

import com.avada.kino.models.News;
import com.avada.kino.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/news")
@RequiredArgsConstructor
public class NewsControllerAdmin {

    private final NewsService newsService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("all_news", newsService.getAll());
        return "/admin/news-admin";
    }

    @GetMapping("/add")
    public String addNewsPage(Model model) {
        model.addAttribute("news", new News());
        return "/admin/news-admin-add";
    }

    @GetMapping("/{id}")
    public String getConcreteNews(@PathVariable int id, Model model) {
        model.addAttribute("the_news", newsService.getById(id));
        return "/admin/news-admin-concrete";
    }

    @PostMapping("/add")
    public String addNews(
            News news,
            @RequestParam("main-image") MultipartFile image,
            @RequestParam("image-pick-input-multiple") MultipartFile[] gallery
    ) {
        newsService.saveWithFiles(news, image, gallery);
        return "redirect:/admin/news";
    }

    @PostMapping("update")
    public String update(
            News news,
            @RequestParam("image-input") MultipartFile image,
            @RequestParam("image-pick-input-multiple") MultipartFile[] gallery
    ) {
        newsService.updateWithFiles(news, image, gallery);
        return "redirect:/admin/news/" + news.getId();
    }

    @GetMapping(value = "/gallery/delete", params = {"news_id", "image_name"})
    public String deleteFromGallery(@RequestParam("news_id") int newsId, @RequestParam("image_name") String imageName) {
        newsService.deleteFromGallery(newsId, imageName);
        return "redirect:/admin/news/" + newsId;
    }

    @PostMapping("/image/delete")
    public String deleteMainImage(@RequestParam("news_id") int newsId, @RequestParam("image_name") String imageName) {
        newsService.deleteMainImage(newsId, imageName);
        return "redirect:/admin/news/" + newsId;
    }
}
