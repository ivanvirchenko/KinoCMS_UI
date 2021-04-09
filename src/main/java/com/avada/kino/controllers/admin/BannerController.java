package com.avada.kino.controllers.admin;

import com.avada.kino.service.BannerService;
import com.avada.kino.service.FilesService;
import com.avada.kino.util.UploadPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.avada.kino.util.UploadPaths.BANNER_UPLOAD_DIR;

@Controller
@RequestMapping("/admin/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;
    private final FilesService filesService;

    @GetMapping
    public String getBanners(Model model) {
        model.addAttribute("banners", bannerService.getAll());
        return "/admin/banners";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("banner", bannerService.getById(id));
        return "/admin/banner-edit";
    }

    @PostMapping("/delete")
    public String deletePicture(@RequestParam String picture, @RequestParam("banner_id") int id) {
        filesService.deleteFile(picture, BANNER_UPLOAD_DIR);
        return "redirect:/admin/banners/" + id;
    }
}
