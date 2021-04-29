package com.avada.kino.service;

import com.avada.kino.dao.MovieDao;
import com.avada.kino.models.Image;
import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.avada.kino.util.UploadPaths.MOVIES_UPLOAD_PATH;

@Service
@RequiredArgsConstructor
public class MovieService implements DaoService<Movie> {

    private final MovieDao dao;
    private final FileService fileService;

    @Override
    public void save(Movie movie) {
        dao.save(movie);
    }

    public void saveWithFiles(Movie movie, MultipartFile file, MultipartFile[] files) {
        if (movie.getGallery() == null) {
            movie.setGallery(new ArrayList<>());
        }
        saveSingleFile(file, movie);
        saveMultipleFiles(files, movie);
        save(movie);
    }

    @Override
    public List<Movie> getAll() {
        return dao.getAll();
    }

    public List<Movie> getCurrent() {
        return dao.getCurrent();
    }

    public List<Movie> getFuture() {
        return dao.getFuture();
    }

    @Override
    public Movie getById(int id) {
        return dao.getById(id);
    }

    @Override
    public void update(Movie movie) {
        dao.update(movie);
    }

    public void updateWithFiles(Movie movie, MultipartFile file, MultipartFile[] files) {
        if (movie.getGallery() == null) {
            movie.setGallery(new ArrayList<>());
        }
        saveSingleFile(file, movie);
        saveMultipleFiles(files, movie);
        update(movie);
    }

    @Override
    public void delete(int id) {
        Movie movie = getById(id);
        deleteImage(id, movie.getLogo().getName());
        movie.getGallery().forEach(image -> deleteImage(id, image.getName()));
        dao.delete(id);
    }

    public List<MovieType> getAllTypes() {
        return dao.getAllTypes();
    }

    public void deleteFromGallery(int movieId, String imageNme) {
        Movie movie = getById(movieId);
        fileService.deleteFile(imageNme, MOVIES_UPLOAD_PATH);
        movie.getGallery().removeIf(image -> image.getName().equals(imageNme));
        update(movie);
    }

    public void deleteImage(int movieId, String imageNme) {
        Movie movie = getById(movieId);
        fileService.deleteFile(imageNme, MOVIES_UPLOAD_PATH);
        movie.setLogo(null);
        update(movie);
    }

    private void saveSingleFile(MultipartFile file, Movie movie) {
        if (!file.isEmpty()) {
            String fileName = fileService.saveFile(file, MOVIES_UPLOAD_PATH);
            movie.setLogo(new Image(fileName, File.separator + MOVIES_UPLOAD_PATH + File.separator + fileName));
        }
    }

    private void saveMultipleFiles(MultipartFile[] files, Movie movie) {
        if (!files[0].isEmpty()) {
            for (MultipartFile multipartFile : files) {
                String fileName = fileService.saveFile(multipartFile, MOVIES_UPLOAD_PATH);
                movie.addToGallery(new Image(fileName, File.separator + MOVIES_UPLOAD_PATH + File.separator + fileName));
            }
        }
    }
}
