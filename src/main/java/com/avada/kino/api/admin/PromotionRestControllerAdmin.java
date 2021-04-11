package com.avada.kino.api.admin;

import com.avada.kino.models.Promotion;
import com.avada.kino.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/promotion")
@RequiredArgsConstructor
public class PromotionRestControllerAdmin {
    private final PromotionService promotionService;

    @GetMapping
    public List<Promotion> getAll() {
        return promotionService.getAll();
    }

    @GetMapping("/{id}")
    public Promotion getById(@PathVariable int id) {
        return promotionService.getById(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody Promotion promotion) {
        promotionService.save(promotion);
    }

    @PutMapping("/update")
    public void update(@RequestBody Promotion promotion) {
        promotionService.update(promotion);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("promotion_id") int promotionId) {
        promotionService.delete(promotionId);
    }
}
