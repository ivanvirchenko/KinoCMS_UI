package com.avada.kino.controller.admin;

import com.avada.kino.models.Cinema;
import com.avada.kino.models.Hall;
import com.avada.kino.models.Image;
import com.avada.kino.service.CinemaService;
import com.avada.kino.service.HallService;
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

import static com.avada.kino.util.UtilConstant.CINEMA_ATTRIBUTE;
import static com.avada.kino.util.UtilConstant.IMAGE_ERROR;

@Controller
@RequestMapping("/admin/hall")
@RequiredArgsConstructor
public class HallAdminController {
    private final CinemaService cinemaService;
    private final HallService hallService;

    @GetMapping(value = "/add")
    public String hallForm(
            Model model,
            HttpSession session,
            @RequestParam("cinema-id") int cinemaId
    ) {
        model.addAttribute("hall", new Hall());
        session.setAttribute("cinema-id", cinemaId);
        return "/admin/hall-admin-add";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        Hall hall = hallService.getById(id);
        model.addAttribute("hall", hall);
        return "/admin/hall-admin-concrete";
    }

    @PostMapping("/save")
    public String save(
            @Valid Hall hall,
            BindingResult bindingResult,
            HttpSession session,
            @RequestParam("rows-count") int rowsCount,
            @RequestParam("place-count") int placeCount,
            @RequestParam("logo-image") MultipartFile logo,
            @RequestParam("banner-image") MultipartFile banner,
            @RequestParam("gallery-images") MultipartFile[] gallery
    ) {
        if (logo.isEmpty()) {
            bindingResult.addError(
                    new FieldError("hall", "logo", IMAGE_ERROR)
            );
        }
        if (bindingResult.hasErrors()) {
            return "/admin/hall-admin-add";
        }
        int cinemaId = (int) session.getAttribute("cinema-id");
        Cinema cinema = cinemaService.getById(cinemaId);
        hall.fillTheHall(rowsCount, placeCount);
        hallService.saveHallImages(logo, banner, gallery, hall);
        cinema.addHall(hall);
        cinemaService.update(cinema);
        session.removeAttribute("cinema-id");
        return "redirect:/admin/cinema/" + cinema.getId();
    }

    @PostMapping("/update")
    public String update(
        @Valid Hall hall,
        BindingResult bindingResult,
        @RequestParam("rows-count") int rowsCount,
        @RequestParam("place-count") int placeCount,
        @RequestParam("logo-image") MultipartFile logo,
        @RequestParam("banner-image") MultipartFile banner,
        @RequestParam("gallery-images") MultipartFile[] gallery
    ) {
        if (hall.getLogo() == null) {
            if (logo.isEmpty()) {
                bindingResult.addError(
                        new FieldError("hall", "logo", IMAGE_ERROR)
                );
            }
        }
        if (bindingResult.hasErrors()) {
            return "/admin/hall-admin-concrete";
        }
        hall.fillTheHall(rowsCount, placeCount);
        hallService.saveHallImages(logo, banner, gallery, hall);
        hallService.save(hall);
        return "redirect:/admin/hall/" + hall.getId();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("hall-id") int hallId, @RequestParam("cinema-id") int cinemaId) {
        hallService.delete(hallId);
        return "redirect:/admin/cinema/" + cinemaId;
    }

    @PostMapping("/logo/delete")
    public String deleteLogo(
            @RequestParam("hall_id") int hallId
    ) {
        hallService.deleteLogo(hallId);
        return "redirect:/admin/hall/" + hallId;
    }

    @PostMapping("/banner/delete")
    public String deleteBanner(
            @RequestParam("hall_id") int hallId
    ) {
        hallService.deleteBanner(hallId);
        return "redirect:/admin/hall/" + hallId;
    }

    @PostMapping("/gallery/delete")
    public String deleteLogo(
            @RequestParam("hall_id") int hallId,
            @RequestParam("image-name") String imageName
    ) {
        hallService.deleteFromGallery(hallId, imageName);
        return "redirect:/admin/hall/" + hallId;
    }
}
