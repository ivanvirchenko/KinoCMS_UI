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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/hall")
@RequiredArgsConstructor
public class HallAdminController {
    private final CinemaService cinemaService;
    private final HallService hallService;

    @GetMapping(value = "/add")
    public String addHallForm(Model model, @RequestParam(required = false) Integer id, HttpSession httpSession) {
        if (id != null) {
            Cinema cinema = cinemaService.getById(id);
            httpSession.setAttribute("cinema", cinema);
        }
        model.addAttribute("hall", new Hall());
        return "/admin/hall-admin-add";
    }

    @PostMapping("/save")
    public String save(
            @Valid Hall hall,
            BindingResult bindingResult,
            @RequestParam("rows-count") Integer rows,
            @RequestParam("place-count") Integer places,
            HttpSession session,
            Model model,
            @RequestParam("logo-image") MultipartFile logo,
            @RequestParam("banner-image") MultipartFile banner,
            @RequestParam("gallery-images") MultipartFile[] gallery
    ) {
        if (rows == 0 || places == 0) {
            bindingResult.addError(new FieldError("hall", "places", "Количество рядов и мест в ряду должно быть больше нуля"));
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("rows-count", rows);
            model.addAttribute("place-count", places);
            return "/admin/hall-admin-add";
        }
        Cinema cinema = (Cinema) session.getAttribute("cinema");
        hall.fillTheHall(rows, places);
        hallService.saveHallImages(logo, banner, gallery, hall);
        cinema.addHall(hall);
        cinemaService.update(cinema);
        session.invalidate();
        return "redirect:/admin/cinema/" + cinema.getId();
    }
}
