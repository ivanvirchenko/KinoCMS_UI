package com.avada.kino.service;

import com.avada.kino.models.Cinema;
import com.avada.kino.models.Image;
import com.avada.kino.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

import static com.avada.kino.util.UploadPaths.CINEMA_UPLOAD_PATH;

@Service
@RequiredArgsConstructor
public class CinemaService implements DaoService<Cinema> {

    private final CinemaRepository repository;
    private final FileService fileService;

    @Override
    public void save(Cinema cinema) {
        repository.save(cinema);
    }

    public void saveWithFiles(
            Cinema cinema,
            MultipartFile logo,
            MultipartFile banner,
            MultipartFile[] gallery
    ) {
        saveLogo(cinema, logo);
        saveBanner(cinema, banner);
        saveToGallery(cinema, gallery);

        save(cinema);
    }

    @Override
    public List<Cinema> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Cinema getById(int id) {
        return repository.findById(id);
    }

    @Override
    public void update(Cinema cinema) {
       repository.save(cinema);
    }

    public void updateWithFiles(Cinema cinema, MultipartFile logo, MultipartFile banner, MultipartFile[] gallery) {
        saveLogo(cinema, logo);
        saveBanner(cinema, banner);
        saveToGallery(cinema, gallery);
        update(cinema);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    private void saveLogo(Cinema cinema, MultipartFile file) {
        if (!file.isEmpty()) {
            String uniqName = fileService.saveFile(file, CINEMA_UPLOAD_PATH);
            cinema.setLogo(new Image(uniqName, File.separator + CINEMA_UPLOAD_PATH + File.separator + uniqName));
        }
    }

    private void saveBanner(Cinema cinema, MultipartFile file) {
        if (!file.isEmpty()) {
            String uniqName = fileService.saveFile(file, CINEMA_UPLOAD_PATH);
            cinema.setBanner(new Image(uniqName, File.separator + CINEMA_UPLOAD_PATH + File.separator + uniqName));
        }
    }

    private void saveToGallery(Cinema cinema, MultipartFile[] files) {
        if (!files[0].isEmpty()) {
            for (MultipartFile file : files) {
                String uniqName = fileService.saveFile(file, CINEMA_UPLOAD_PATH);
                cinema.addToGallery(new Image(uniqName, File.separator + CINEMA_UPLOAD_PATH + File.separator + uniqName));
            }
        }
    }

    public void deleteLogo(String logoName, int cinemaId) {
        fileService.deleteFile(logoName, CINEMA_UPLOAD_PATH);
        Cinema cinema = getById(cinemaId);
        cinema.setLogo(null);
        update(cinema);
    }
    public void deleteBanner(String name, int cinemaId) {
        fileService.deleteFile(name, CINEMA_UPLOAD_PATH);
        Cinema cinema = getById(cinemaId);
        cinema.setBanner(null);
        update(cinema);
    }
    public void deleteFromGallery(String name, int cinemaId) {
        fileService.deleteFile(name, CINEMA_UPLOAD_PATH);
        Cinema cinema = getById(cinemaId);
        cinema.getGallery().removeIf(image -> image.getName().equals(name));
        update(cinema);
    }
}
