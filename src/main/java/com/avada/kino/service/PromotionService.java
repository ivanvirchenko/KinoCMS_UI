package com.avada.kino.service;

import com.avada.kino.models.Image;
import com.avada.kino.models.Promotion;
import com.avada.kino.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.avada.kino.util.UploadPaths.PROM_UPLOAD_DIR;

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository repository;
    private final FilesService filesService;

    public List<Promotion> getAll() {
        return repository.getAll();
    }

    public List<Promotion> getEnabled(boolean enabled) {
        return repository.getEnabled(enabled);
    }

    public Promotion getById(int id) {
        return repository.getById(id);
    }

    public void save(Promotion promotion, MultipartFile mainImage, MultipartFile[] gallery) {
        saveImage(mainImage, promotion);
        saveGalleryImages(gallery, promotion);
        repository.save(promotion);
    }

    public void update(Promotion promotion, MultipartFile mainImage, MultipartFile[] gallery) {
        saveImage(mainImage, promotion);
        saveGalleryImages(gallery, promotion);
        repository.update(promotion);
    }

    public void delete(int id) {
        Promotion promotion = repository.getById(id);
        repository.delete(id);

        filesService.deleteFile(promotion.getImage().getName(), PROM_UPLOAD_DIR);
        if (!promotion.getGallery().isEmpty()) {
            promotion.getGallery().forEach(
                    image -> filesService.deleteFile(image.getName(), PROM_UPLOAD_DIR)
            );
        }
    }

    public Integer getTotalPromCount() {
        return repository.getTotalPromCount();
    }

    public Integer getEnabledPromCount() {
        return repository.getEnabledPromCount();
    }

    public Integer getDisabledPromCount() {
        return repository.getDisabledPromCount();
    }

    private void saveImage(MultipartFile mainImage, Promotion promotion) {
        if (!mainImage.isEmpty()) {
            String url = filesService.uploadFile(mainImage, PROM_UPLOAD_DIR);
            promotion.setImage(new Image(mainImage.getOriginalFilename(), url));
        }
    }
    private void saveGalleryImages(MultipartFile[] gallery, Promotion promotion) {
        if (!gallery[0].isEmpty()) {
            for (MultipartFile multipartFile : gallery) {
                String url = filesService.uploadFile(multipartFile, PROM_UPLOAD_DIR);
                promotion.addToGallery(new Image(multipartFile.getOriginalFilename(), url));
            }
        }
    }
}
