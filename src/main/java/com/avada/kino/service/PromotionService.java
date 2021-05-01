package com.avada.kino.service;

import com.avada.kino.models.Image;
import com.avada.kino.models.Promotion;
import com.avada.kino.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.avada.kino.util.UploadPaths.PROMOTION_UPLOAD_PATH;

@Service
@RequiredArgsConstructor
public class PromotionService implements DaoService<Promotion>{
    private final FileService fileService;
    private final PromotionRepository repository;

    @Override
    public void save(Promotion promotion) {
        if (promotion.getGallery() == null) {
            promotion.setGallery(new ArrayList<>());
        }
        repository.save(promotion);
    }

    public void saveWithFiles(Promotion promotion, MultipartFile file, MultipartFile[] files) {
        saveSingleFile(file, promotion);
        saveMultipleFiles(files, promotion);
        save(promotion);
    }

    @Override
    public List<Promotion> getAll() {
        return repository.findAll();
    }

    @Override
    public Promotion getById(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Акция с таким идентификатором не найдена"));
    }

    @Override
    public void update(Promotion promotion) {
        repository.save(promotion);
    }

    public void updateWithFiles(Promotion promotion, MultipartFile file, MultipartFile[] files) {
        if (promotion.getGallery() == null) {
            promotion.setGallery(new ArrayList<>());
        }
        saveSingleFile(file, promotion);
        saveMultipleFiles(files, promotion);
        update(promotion);
    }

    @Override
    public void delete(int id) {
        Promotion promotion = getById(id);
        fileService.deleteFile(promotion.getLogo().getName(), PROMOTION_UPLOAD_PATH);
        promotion.getGallery().forEach(image -> fileService.deleteFile(image.getName(), PROMOTION_UPLOAD_PATH));
        repository.delete(promotion);
    }

    public void deleteImage(int promotionId, String imageName) {
        Promotion promotion = getById(promotionId);
        fileService.deleteFile(imageName, PROMOTION_UPLOAD_PATH);
        promotion.setLogo(null);
        update(promotion);
    }

    public void deleteFromGallery(int promotionId, String imageNme) {
        Promotion promotion = getById(promotionId);
        fileService.deleteFile(imageNme, PROMOTION_UPLOAD_PATH);
        promotion.getGallery().removeIf(image -> image.getName().equals(imageNme));
        update(promotion);
    }

    private void saveSingleFile(MultipartFile file, Promotion promotion) {
        if (!file.isEmpty()) {
            String fileName = fileService.saveFile(file, PROMOTION_UPLOAD_PATH);
            promotion.setLogo(new Image(fileName, File.separator + PROMOTION_UPLOAD_PATH + File.separator + fileName));
        }
    }

    private void saveMultipleFiles(MultipartFile[] files, Promotion promotion) {
        if (!files[0].isEmpty()) {
            for (MultipartFile multipartFile : files) {
                String fileName = fileService.saveFile(multipartFile, PROMOTION_UPLOAD_PATH);
                promotion.addToGallery(new Image(fileName, File.separator + PROMOTION_UPLOAD_PATH + File.separator + fileName));
            }
        }
    }
}
