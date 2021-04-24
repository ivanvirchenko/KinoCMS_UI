package com.avada.kino.service;

import com.avada.kino.models.Hall;
import com.avada.kino.models.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static com.avada.kino.util.UploadPaths.HALLS_UPLOAD_PATH;

@Service
@RequiredArgsConstructor
public class HallService {
    private final FileService fileService;
    private static final String UPLOAD_DEST = File.separator + HALLS_UPLOAD_PATH + File.separator;

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
}
