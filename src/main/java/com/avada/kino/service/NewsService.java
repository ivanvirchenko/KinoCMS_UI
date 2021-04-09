package com.avada.kino.service;

import com.avada.kino.models.Image;
import com.avada.kino.models.News;
import com.avada.kino.repository.NewsRepository;
import com.avada.kino.util.UploadPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.avada.kino.util.UploadPaths.NEWS_UPLOAD_DIR;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository repository;
    private final FilesService filesService;

    public List<News> getAll() {
        return repository.getAll();
    }

    public List<News> getEnabled(boolean enabled) {
        return repository.getEnabled(enabled);
    }

    public News getById(int id) {
        return repository.getById(id);
    }

    public void save(News news, MultipartFile mainImage, MultipartFile[] gallery) {
        if (!mainImage.isEmpty()) {
            saveImage(mainImage, news);
        }
        if (!gallery[0].isEmpty()) {
            saveImages(gallery, news);
        }
        repository.save(news);
    }

    public void update(News news, MultipartFile mainImage, MultipartFile[] gallery) {
        if (!mainImage.isEmpty()) {
            saveImage(mainImage, news);
        }
        if (!gallery[0].isEmpty()) {
            saveImages(gallery, news);
        }
        repository.update(news);
    }

    public void delete(int id) {
        News news = repository.getById(id);
        repository.delete(id);

        filesService.deleteFile(news.getImage().getName(), NEWS_UPLOAD_DIR);
        if (!news.getGallery().isEmpty()) {
            news.getGallery().forEach(
                    image -> filesService.deleteFile(image.getName(), NEWS_UPLOAD_DIR)
            );
        }
    }

    public Integer getTotalNewsCount() {
        return repository.getTotalNewsCount();
    }

    public Integer getEnabledNewsCount() {
        return repository.getEnabledNewsCount();
    }

    public Integer getDisabledNewsCount() {
        return repository.getDisabledNewsCount();
    }

    private void saveImage(MultipartFile mainImage, News news) {
        String url = filesService.uploadFile(mainImage, NEWS_UPLOAD_DIR);
        news.setImage(new Image(mainImage.getOriginalFilename(), url));
    }

    private void saveImages(MultipartFile[] files, News news) {
        for (MultipartFile file : files) {
            String url = filesService.uploadFile(file, NEWS_UPLOAD_DIR);
            news.addToGallery(new Image(file.getOriginalFilename(), url));
        }
    }
}
