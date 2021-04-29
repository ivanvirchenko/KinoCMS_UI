package com.avada.kino.controller.admin;

import com.avada.kino.models.News;
import com.avada.kino.service.NewsService;
import com.avada.kino.util.StringsConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.Optional;

import static com.avada.kino.util.StringsConstant.REQUIRED;

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
        model.addAttribute("news", newsService.getById(id));
        return "/admin/news-admin-concrete";
    }

    @PostMapping("/save")
    public String addNews(
            @Valid News news,
            BindingResult bindingResult,
            @RequestParam("logo-image") MultipartFile image,
            @RequestParam("gallery-images") MultipartFile[] gallery
    ) {
        if (image == null || image.isEmpty()) {
            FieldError imageError = new FieldError("news", "logo", REQUIRED);
            bindingResult.addError(imageError);
        }
        if (bindingResult.hasErrors()) {
            return "/admin/news-admin-add";
        }

        newsService.saveWithFiles(news, image, gallery);
        return "redirect:/admin/news/" + news.getId();
    }

    @PostMapping("/update")
    public String update(
            @Valid News news,
            BindingResult bindingResult,
            @RequestParam("logo-image") MultipartFile image,
            @RequestParam("gallery-images") MultipartFile[] gallery
    ) {
        if (news.getLogo() == null) {
            if (image.isEmpty()) {
                FieldError imageError = new FieldError("news", "logo", REQUIRED);
                bindingResult.addError(imageError);
            }
        }
        if (bindingResult.hasErrors()) {
            return "/admin/news-admin-concrete";
        }
        newsService.updateWithFiles(news, image, gallery);
        return "redirect:/admin/news/" + news.getId();
    }

    @PostMapping("/logo/delete")
    public String deleteLogo(@RequestParam("news_id") int newsId, @RequestParam("logo-image") String imageName) {
        newsService.deleteImage(newsId, imageName);
        return "redirect:/admin/news/" + newsId;
    }

    @PostMapping("/gallery/delete")
    public String deleteFromGallery(@RequestParam("news_id") int newsId, @RequestParam("image-name") String imageName) {
        newsService.deleteFromGallery(newsId, imageName);
        return "redirect:/admin/news/" + newsId;
    }
}
