package com.avada.kino.controller.admin;

import com.avada.kino.models.Cinema;
import com.avada.kino.models.City;
import com.avada.kino.service.CinemaService;
import com.avada.kino.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static com.avada.kino.util.UtilConstant.CINEMA_ATTRIBUTE;
import static com.avada.kino.util.UtilConstant.IMAGE_ERROR;

@Controller
@RequestMapping("/admin/cinema")
@RequiredArgsConstructor
public class CinemaAdminController {
    private final CinemaService cinemaService;
    private final CityService cityService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("cinemaList", cinemaService.getAll());
        return "/admin/cinema-admin";
    }

    @GetMapping("/add")
    public String newCinemaForm(Model model) {
        model.addAttribute("cinema", new Cinema());
        return "/admin/cinema-admin-add";
    }

    @GetMapping("/{id}")
    public String getCinema(@PathVariable int id, Model model) {
        model.addAttribute(CINEMA_ATTRIBUTE, cinemaService.getById(id));
        return "/admin/cinema-admin-concrete";
    }

    @PostMapping("/save")
    public String save(
            @Valid Cinema cinema,
            BindingResult bindingResult,
            @RequestParam("city") int cityId,
            @RequestParam("logo-image") MultipartFile logo,
            @RequestParam("banner-image") MultipartFile banner,
            @RequestParam("gallery-images") MultipartFile[] gallery
    ) {
        if (cinema.getLogo() == null) {
            if (logo.isEmpty()) {
                bindingResult.addError(
                        new FieldError("cinema", "logo", IMAGE_ERROR)
                );
            }
        }
        if (bindingResult.hasErrors()) {
            return "/admin/cinema-admin-concrete";
        }
        cinema.setCity(cityService.getById(cityId));
        cinemaService.saveWithFiles(cinema, logo, banner, gallery);
        return "redirect:/admin/cinema/" + cinema.getId();
    }



    @PostMapping("/logo/delete")
    public String deleteLogoImage(@RequestParam("cinema-id") Integer id){
        cinemaService.deleteLogo(id);
        return "redirect:/admin/cinema/" + id;
    }
    @PostMapping("/banner/delete")
    public String deleteBannerImage(@RequestParam("cinema-id") Integer id){
        cinemaService.deleteBanner(id);
        return "redirect:/admin/cinema/" + id;
    }
    @PostMapping("/gallery/delete")
    public String deleteGalleryImage(@RequestParam("image-name") String name, @RequestParam("cinema-id") Integer id){
        cinemaService.deleteFromGallery(name, id);
        return "redirect:/admin/cinema/" + id;
    }

    @ModelAttribute
    private List<City> cityList() {
        return cityService.getAll();
    }
}
