package com.avada.kino.controllers.admin;

import com.avada.kino.models.Image;
import com.avada.kino.models.News;
import com.avada.kino.service.FilesService;
import com.avada.kino.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.avada.kino.util.UploadPaths.NEWS_UPLOAD_DIR;

@Controller
@RequestMapping("/admin/news")
@RequiredArgsConstructor
public class AdminNewsController {

    private final NewsService newsService;
    private final FilesService fileService;

    @GetMapping
    public String getAllNews(Model model) {
        model.addAttribute("all_news", newsService.getAll());
        return "/admin/all_news_admin";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute("the_news", newsService.getById(id));
        return "/admin/concrete_news";
    }

    @GetMapping("/add")
    public String addNewsGet() {
        return "/admin/add_news_admin";
    }

    @PostMapping("/add")
    public String addNewsPost(
            @RequestParam("main_image") MultipartFile mainImage,
            @RequestParam("gallery_images") MultipartFile[] gallery,
            News news
    ) {
        newsService.save(news, mainImage, gallery);
        return "redirect:/admin/news";
    }

    @PostMapping("/update")
    public String updateNews(
            @RequestParam("main_image") MultipartFile mainImage,
            @RequestParam("gallery_images") MultipartFile[] gallery,
            News news
    ) {
        newsService.update(news, mainImage, gallery);
        return "redirect:/admin/news";
    }

    @PostMapping("/delete")
    public String deleteNews(
            @RequestParam int newsId) {
        newsService.delete(newsId);
        return "redirect:/admin/news";
    }

    @PostMapping("/file/delete")
    public String deleteImage(@RequestParam("image_name") String imageName, @RequestParam("news_id") int id) {
        fileService.deleteFile(imageName, NEWS_UPLOAD_DIR);
        return "redirect:/admin/news/" + id;
    }

    @ModelAttribute
    private News news() {
        return new News();
    }


}
