package com.avada.kino.controller.admin;

import com.avada.kino.models.Promotion;
import com.avada.kino.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/promotion")
@RequiredArgsConstructor
public class PromotionControllerAdmin {
    private final PromotionService promotionService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("all_promotion", promotionService.getAll());
        return "/admin/promos-admin";
    }

    @GetMapping("/add")
    public String addPromotionPage(Model model) {
        model.addAttribute("promotion", new Promotion());
        return "/admin/promotion-admin-add";
    }

    @GetMapping("/{id}")
    public String getConcretePromotion(@PathVariable int id, Model model) {
        model.addAttribute("the_promotion", promotionService.getById(id));
        return "/admin/promotion-admin";
    }

    @PostMapping("/add")
    public String addPromotion(
            Promotion promotion,
            @RequestParam("main-image") MultipartFile image,
            @RequestParam("image-pick-input-multiple") MultipartFile[] gallery
    ) {
        promotionService.saveWithFiles(promotion, image, gallery);
        return "redirect:/admin/promotion";
    }

    @PostMapping("update")
    public String update(
            Promotion promotion,
            @RequestParam("image-input") MultipartFile image,
            @RequestParam("image-pick-input-multiple") MultipartFile[] gallery
    ) {
        promotionService.updateWithFiles(promotion, image, gallery);
        return "redirect:/admin/promotion/" + promotion.getId();
    }

    @GetMapping(value = "/gallery/delete", params = {"promotion_id", "image_name"})
    public String deleteFromGallery(@RequestParam("promotion_id") int promotionId, @RequestParam("image_name") String imageName) {
        promotionService.deleteFromGallery(promotionId, imageName);
        return "redirect:/admin/promotion/" + promotionId;
    }

    @PostMapping("/image/delete")
    public String deleteMainImage(@RequestParam("promotion_id") int promotionId, @RequestParam("image_name") String imageName) {
        promotionService.deleteMainImage(promotionId, imageName);
        return "redirect:/admin/promotion/" + promotionId;
    }
}
