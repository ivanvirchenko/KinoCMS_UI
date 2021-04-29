package com.avada.kino.service;

import com.avada.kino.models.Image;
import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import com.avada.kino.repository.MovieRepository;
import com.avada.kino.repository.MovieTypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.avada.kino.util.UploadPaths.MOVIES_UPLOAD_PATH;

@Service
@RequiredArgsConstructor
public class MovieService implements DaoService<Movie> {

    private final MovieRepository repository;
    private final MovieTypesRepository typesRepository;
    private final FileService fileService;

    @Override
    public void save(Movie movie) {
        repository.save(movie);
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
        return repository.findAll();
    }

    public List<Movie> getCurrent() {
        return repository.getCurrent(LocalDate.now());
    }

    public List<Movie> getFuture() {
        return repository.getFuture(LocalDate.now());
    }

    @Override
    public Movie getById(int id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void update(Movie movie) {
        repository.save(movie);
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
        fileService.deleteFile(movie.getLogo().getName(), MOVIES_UPLOAD_PATH);
        movie.getGallery().forEach(image -> fileService.deleteFile(image.getName(), MOVIES_UPLOAD_PATH));
        repository.delete(movie);
    }

    public List<MovieType> getAllTypes() {
        return typesRepository.findAll();
    }

    public void deleteFromGallery(int movieId, String imageNme) {
        Movie movie = getById(movieId);
        fileService.deleteFile(imageNme, MOVIES_UPLOAD_PATH);
        movie.getGallery().removeIf(image -> image.getName().equals(imageNme));
        update(movie);
    }

    public void deleteLogo(int movieId, String imageName) {
        Movie movie = getById(movieId);
        fileService.deleteFile(imageName, MOVIES_UPLOAD_PATH);
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
