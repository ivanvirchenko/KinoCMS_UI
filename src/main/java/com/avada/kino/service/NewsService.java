package com.avada.kino.service;

import com.avada.kino.models.Image;
import com.avada.kino.models.News;
import com.avada.kino.models.News;
import com.avada.kino.dao.NewsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.avada.kino.util.UploadPaths.NEWS_UPLOAD_PATH;
import static com.avada.kino.util.UploadPaths.NEWS_UPLOAD_PATH;

@Service
@RequiredArgsConstructor
public class NewsService implements DaoService<News> {

    private final NewsDao dao;
    private final FileService fileService;

    @Override
    public void save(News news) {
        if (news.getGallery() == null) {
            news.setGallery(new ArrayList<>());
        }
        dao.save(news);
    }

    public void saveWithFiles(News news, MultipartFile file, MultipartFile[] files) {
        saveSingleFile(file, news);
        saveMultipleFiles(files, news);
        save(news);
    }

    @Override
    public List<News> getAll() {
        return dao.getAll();
    }

    @Override
    public News getById(int id) {
        return dao.getById(id);
    }

    @Override
    public void update(News news) {
        dao.update(news);
    }

    public void updateWithFiles(News news, MultipartFile file, MultipartFile[] files) {
        if (news.getGallery() == null) {
            news.setGallery(new ArrayList<>());
        }
        saveSingleFile(file, news);
        saveMultipleFiles(files, news);
        update(news);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

    public void deleteFromGallery(int newsId, String imageNme) {
        News news = getById(newsId);
        fileService.deleteFile(imageNme, NEWS_UPLOAD_PATH);
        news.getGallery().removeIf(image -> image.getName().equals(imageNme));
        update(news);
    }

    public void deleteMainImage(int newsId, String imageNme) {
        News news = getById(newsId);
        fileService.deleteFile(imageNme, NEWS_UPLOAD_PATH);
        news.setLogo(null);
        update(news);
    }

    private void saveSingleFile(MultipartFile file, News news) {
        if (!file.isEmpty()) {
            String fileName = fileService.saveFile(file, NEWS_UPLOAD_PATH);
            news.setLogo(new Image(fileName, File.separator + NEWS_UPLOAD_PATH + File.separator + fileName));
        }
    }

    private void saveMultipleFiles(MultipartFile[] files, News news) {
        if (!files[0].isEmpty()) {
            for (MultipartFile multipartFile : files) {
                String fileName = fileService.saveFile(multipartFile, NEWS_UPLOAD_PATH);
                news.addToGallery(new Image(fileName, File.separator + NEWS_UPLOAD_PATH + File.separator + fileName));
            }
        }
    }
}
