package com.avada.kino.controllers.admin;

import com.avada.kino.models.Promotion;
import com.avada.kino.service.FilesService;
import com.avada.kino.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.avada.kino.util.UploadPaths.NEWS_UPLOAD_DIR;
import static com.avada.kino.util.UploadPaths.PROM_UPLOAD_DIR;

@Controller
@RequestMapping("/admin/promotion")
@RequiredArgsConstructor
public class AdminPromotionController {
    private final PromotionService promotionService;
    private final FilesService filesService;

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("all_promotions", promotionService.getAll());
        return "/admin/all_promotion_admin";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute("prom", promotionService.getById(id));
        return "admin/concrete_prom_admin";
    }

    @GetMapping("/add")
    public String getAddingPage(Model model) {
        model.addAttribute("new_promotion", new Promotion());
        return "/admin/add_promotion_admin";
    }

    @PostMapping("/add")
    public String addPromotion(
            @RequestParam("main_image") MultipartFile mainImage,
            @RequestParam("gallery_images") MultipartFile[] galleryImages,
            Promotion promotion
    ) {
        promotionService.save(promotion, mainImage, galleryImages);
        return "redirect:/admin/promotion";
    }

    @PostMapping("/update")
    public String updatePromotion(
            @RequestParam("main_image") MultipartFile mainImage,
            @RequestParam("gallery_images") MultipartFile[] galleryImages,
            Promotion promotion
    ) {
        promotionService.update(promotion, mainImage, galleryImages);
        return "redirect:/admin/promotion/" + promotion.getId();
    }

    @PostMapping("/delete")
    public String Promotion(
            @RequestParam int promId) {
        promotionService.delete(promId);
        return "redirect:/admin/promotion";
    }

    @PostMapping("/file/delete")
    public String deleteImage(@RequestParam("image_name") String imageName, @RequestParam("promotion_id") int id) {
        filesService.deleteFile(imageName, PROM_UPLOAD_DIR);
        return "redirect:/admin/promotion/" + id;
    }

}
