package com.avada.kino.service;

import com.avada.kino.models.Hall;
import com.avada.kino.models.Image;
import com.avada.kino.repository.HallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static com.avada.kino.util.UploadPaths.HALLS_UPLOAD_PATH;

@Service
@RequiredArgsConstructor
public class HallService {
    private final FileService fileService;
    private final HallRepository repository;
    private static final String UPLOAD_DEST = File.separator + HALLS_UPLOAD_PATH + File.separator;

    public void save(Hall hall) {
        repository.save(hall);
    }

    public Hall getById(int id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void saveHallImages(MultipartFile logo, MultipartFile banner, MultipartFile[] gallery, Hall hall) {
        saveLogo(logo, hall);
        saveBanner(banner, hall);
        saveGallery(gallery, hall);
    }

    private void saveLogo(MultipartFile logo, Hall hall) {
        if (!logo.isEmpty()) {
            String logoName = fileService.saveFile(logo, HALLS_UPLOAD_PATH);
            hall.setLogo(new Image(logoName, UPLOAD_DEST + logoName));
        }
    }

    private void saveBanner(MultipartFile banner, Hall hall) {
        if (!banner.isEmpty()) {
            String logoName = fileService.saveFile(banner, HALLS_UPLOAD_PATH);
            hall.setBanner(new Image(logoName, UPLOAD_DEST + logoName));
        }
    }

    private void saveGallery(MultipartFile[] gallery, Hall hall) {
        if (!gallery[0].isEmpty()) {
            for (MultipartFile file : gallery) {
                String name = fileService.saveFile(file, HALLS_UPLOAD_PATH);
                hall.addToGallery(new Image(name, UPLOAD_DEST + name));
            }
        }
    }

    public void deleteLogo(int id) {
        Hall hall = getById(id);
        fileService.deleteFile(hall.getLogo().getName(), HALLS_UPLOAD_PATH);
        hall.setLogo(null);
        repository.save(hall);
    }

    public void deleteBanner(int id) {
        Hall hall = getById(id);
        fileService.deleteFile(hall.getBanner().getName(), HALLS_UPLOAD_PATH);
        hall.setBanner(null);
        repository.save(hall);
    }

    public void deleteFromGallery(int id, String imageName) {
        fileService.deleteFile(imageName, HALLS_UPLOAD_PATH);
        Hall hall = getById(id);
        hall.getGallery().removeIf(image -> image.getName().equals(imageName));
        repository.save(hall);
    }
}
