package com.avada.kino.controller.admin;

import com.avada.kino.models.Cinema;
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

import static com.avada.kino.util.StringsConstant.IMAGE_ERROR;

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
        model.addAttribute("cityList", cityService.getAll());
        return "/admin/cinema-admin-add";
    }

    @GetMapping("/{id}")
    public String getCinema(@PathVariable int id, Model model) {
        model.addAttribute("cinema", cinemaService.getById(id));
        model.addAttribute("cityList", cityService.getAll());
        return "/admin/cinema-admin-concrete";
    }

    @PostMapping("/save")
    public String saveCinema(
            @Valid Cinema cinema,
            BindingResult bindingResult,
            Model model,
            HttpSession session,
            @RequestParam(name = "city") int cityId,
            @RequestParam("logo-image") MultipartFile logo,
            @RequestParam("banner-image") MultipartFile banner,
            @RequestParam("gallery-images") MultipartFile[] gallery
    ) {

        if (logo.isEmpty()) {
            FieldError logoError = new FieldError("cinema", "logo", IMAGE_ERROR);
            bindingResult.addError(logoError);
        }
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("cityList", cityService.getAll());
            return "/admin/cinema-admin-add";
        }

        cinema.setCity(cityService.getById(cityId));
        cinemaService.saveWithFiles(cinema, logo, banner, gallery);
        session.setAttribute("cinema", cinema);
        return "redirect:/admin/hall/add";
    }

    @PostMapping("/update")
    public String update(
            @Valid Cinema cinema,
            BindingResult bindingResult,
            @RequestParam("city") int id,
            Model model,
            @RequestParam("logo-image") MultipartFile logo,
            @RequestParam("banner-image") MultipartFile banner,
            @RequestParam("gallery-images") MultipartFile[] gallery
    ) {
        if (logo.isEmpty() && cinema.getLogo() == null) {
            bindingResult.addError(new FieldError("cinema", "logo", IMAGE_ERROR));
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("cityList", cityService.getAll());
            return "/admin/cinema-admin-concrete";
        }
        cinema.setCity(cityService.getById(id));
        cinemaService.updateWithFiles(cinema, logo, banner, gallery);
        return "redirect:/admin/cinema/" + cinema.getId();
    }

    @PostMapping("/logo/delete")
    public String deleteLogoImage(@RequestParam("image-name") String name, @RequestParam("cinema_id") Integer id){
        cinemaService.deleteLogo(name, id);
        return "redirect:/admin/cinema/" + id;
    }
}
