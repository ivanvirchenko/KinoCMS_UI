package com.avada.kino.controller.admin;

import com.avada.kino.models.Cinema;
import com.avada.kino.models.Hall;
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

import static com.avada.kino.util.UtilConstant.CINEMA_ATTRIBUTE;
import static com.avada.kino.util.UtilConstant.IMAGE_ERROR;

@Controller
@RequestMapping("/admin/hall")
@RequiredArgsConstructor
public class HallAdminController {
    private final CinemaService cinemaService;
    private final HallService hallService;

    @GetMapping(value = "/add")
    public String addHall(Model model, @RequestParam(value = "cinema_id", required = false) Integer cinemaId, HttpSession session) {
        if (cinemaId != null) {
            Cinema cinema = cinemaService.getById(cinemaId);
            session.setAttribute(CINEMA_ATTRIBUTE, cinema);
        }
        model.addAttribute("hall", new Hall());
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
            @RequestParam("logo-image") MultipartFile logoImage,
            @RequestParam("banner-image") MultipartFile bannerImage,
            @RequestParam("gallery-images") MultipartFile[] gallery
    ) {
        validate(rowsCount, placeCount, logoImage, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/admin/hall-admin-add";
        }
        Cinema cinema = (Cinema) session.getAttribute(CINEMA_ATTRIBUTE);
        hall.fillTheHall(rowsCount, placeCount);
        hallService.saveHallImages(logoImage, bannerImage, gallery, hall);
        cinema.addHall(hall);
        cinemaService.update(cinema);
        session.invalidate();
        return "redirect:/admin/cinema/" + cinema.getId();
    }

    @PostMapping("/update")
    public String update(
            @Valid Hall hall,
            BindingResult bindingResult,
            HttpSession session,
            @RequestParam("rows-count") int rowsCount,
            @RequestParam("place-count") int placeCount,
            @RequestParam("logo-image") MultipartFile logoImage,
            @RequestParam("banner-image") MultipartFile bannerImage,
            @RequestParam("gallery-images") MultipartFile[] gallery
    ) {
        validate(rowsCount, placeCount, logoImage, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/admin/hall-admin-add";
        }
        Cinema cinema = (Cinema) session.getAttribute(CINEMA_ATTRIBUTE);
        hall.setCinema(cinema);
        hall.fillTheHall(rowsCount, placeCount);
        hallService.saveHallImages(logoImage, bannerImage, gallery, hall);
        hallService.save(hall);
        session.invalidate();
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

    private void validate(int rowsCount, int placeCount, MultipartFile logoImage, BindingResult bindingResult) {
        if(rowsCount <= 0) {
            FieldError rowsError = new FieldError("hall", "places", "Недопустимое значение");
            bindingResult.addError(rowsError);
        }
        if(placeCount <= 0) {
            FieldError rowsError = new FieldError("hall", "places", "Недопустимое значение");
            bindingResult.addError(rowsError);
        }
        if (logoImage.isEmpty()) {
            FieldError rowsError = new FieldError("hall", "logo", IMAGE_ERROR);
            bindingResult.addError(rowsError);
        }
    }
}
