package com.avada.kino.service;

import com.avada.kino.models.Image;
import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import com.avada.kino.repository.MovieRepository;
import com.avada.kino.util.UploadPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.avada.kino.util.UploadPaths.MOVIE_UPLOAD_DIR;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository repository;
    private final FilesService filesService;

    public List<Movie> getShowingMovies() {
        return repository.getShowingMovies();
    }

    public List<Movie> getFutureMovies() {
        return repository.getFutureMovies();
    }

    public Movie getMovieById(int id) {
        return repository.getById(id);
    }

    public void save(Movie movie, MultipartFile main, MultipartFile[] gallery) {
        saveImage(movie, main);
        saveGalleryImages(movie, gallery);
        repository.save(movie);
    }

    public List<MovieType> getTypes() {
        return repository.getTypes();
    }

    public int getTotalMoviesCount() {
        return repository.getTotalMoviesCount();
    }

    public int getCurrentMoviesCount() {
        return repository.getCurrentMoviesCount();
    }

    public int getFutureMoviesCount() {
        return repository.getFutureMoviesCount();
    }

    public void update(Movie movie, MultipartFile main, MultipartFile[] gallery) {
        saveImage(movie, main);
        saveGalleryImages(movie, gallery);
        repository.update(movie);
    }

    private void saveImage(Movie movie, MultipartFile main) {
        if (!main.isEmpty()) {
            String path = filesService.uploadFile(main, MOVIE_UPLOAD_DIR);
            movie.setImage(new Image(main.getOriginalFilename(), path));
        }
    }

    private void saveGalleryImages(Movie movie, MultipartFile[] gallery) {
        if (!gallery[0].isEmpty()) {
            for (MultipartFile multipartFile : gallery) {
                String path = filesService.uploadFile(multipartFile, MOVIE_UPLOAD_DIR);
                movie.addToGallery(new Image(multipartFile.getOriginalFilename(), path));
            }
        }
    }

    public void delete(int id) {
        Movie movie = getMovieById(id);
        repository.delete(id);
        filesService.deleteFile(movie.getImage().getName(), MOVIE_UPLOAD_DIR);
        movie.getGallery().forEach(image -> filesService.deleteFile(image.getName(), MOVIE_UPLOAD_DIR));
    }
}
