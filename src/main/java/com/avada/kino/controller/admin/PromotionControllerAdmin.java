package com.avada.kino.controller.admin;

import com.avada.kino.models.Promotion;
import com.avada.kino.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.avada.kino.util.UtilConstant.IMAGE_ERROR;

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

    @PostMapping("/save")
    public String addPromotion(
            @Valid Promotion promotion,
            BindingResult bindingResult,
            @RequestParam("logo-image") MultipartFile image,
            @RequestParam("gallery-images") MultipartFile[] gallery
    ) {
        if (promotion.getLogo() == null) {
            if (image.isEmpty()) {
                FieldError imageError = new FieldError("promotion", "logo", IMAGE_ERROR);
                bindingResult.addError(imageError);
            }
        }
        if (bindingResult.hasErrors()) {
            return "/admin/promotion-admin-add";
        }

        promotionService.saveWithFiles(promotion, image, gallery);
        return "redirect:/admin/promotion";
    }

    @PostMapping("/update")
    public String update(
            @Valid Promotion promotion,
            BindingResult bindingResult,
            @RequestParam("logo-image") MultipartFile image,
            @RequestParam("gallery-images") MultipartFile[] gallery
    ) {
        if (promotion.getLogo() == null) {
            if (image.isEmpty()) {
                FieldError imageError = new FieldError("promotion", "logo", IMAGE_ERROR);
                bindingResult.addError(imageError);
            }
        }
        if (bindingResult.hasErrors()) {
            return "/admin/promotion-admin-add";
        }
        promotionService.updateWithFiles(promotion, image, gallery);
        return "redirect:/admin/promotion/" + promotion.getId();
    }


    @PostMapping("/logo/delete")
    public String deleteLogo(@RequestParam("promotion_id") int promotionId, @RequestParam("logo-image") String imageName) {
        promotionService.deleteImage(promotionId, imageName);
        return "redirect:/admin/promotion/" + promotionId;
    }

    @PostMapping("/gallery/delete")
    public String deleteFromGallery(@RequestParam("promotion_id") int promotionId, @RequestParam("image-name") String imageName) {
        promotionService.deleteFromGallery(promotionId, imageName);
        return "redirect:/admin/promotion/" + promotionId;
    }
}
