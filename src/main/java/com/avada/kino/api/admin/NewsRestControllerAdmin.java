package com.avada.kino.api.admin;

import com.avada.kino.models.News;
import com.avada.kino.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/news")
@RequiredArgsConstructor
public class NewsRestControllerAdmin {

    private final NewsService newsService;

    @GetMapping
    public List<News> getAll() {
        return newsService.getAll();
    }

    @GetMapping("/{id}")
    public News getById(@PathVariable int id) {
        return newsService.getById(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody News news) {
        newsService.save(news);
    }

    @PutMapping("/update")
    public void update(@RequestBody News news) {
        newsService.update(news);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("news_id") int newsId) {
        newsService.delete(newsId);
    }
}
