package com.avada.kino.api.admin;

import com.avada.kino.models.Cinema;
import com.avada.kino.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cinema")
@RequiredArgsConstructor
public class CinemaRestControllerAdmin {
    private final CinemaService cinemaService;

    @GetMapping
    public List<Cinema> getAll() {
        return cinemaService.getAll();
    }

    @GetMapping("/clean")
    public List<Cinema> getAllWithoutCollections() {
        return cinemaService.getAllWithoutCollections();
    }

    @GetMapping(params = "id")
    public Cinema getById(@RequestParam int id) {
        return cinemaService.getById(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody Cinema cinema) {
        cinemaService.save(cinema);
    }

    @PutMapping("/update")
    public void update(@RequestBody Cinema cinema) {
        cinemaService.update(cinema);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam int cinema_id) {
        cinemaService.delete(cinema_id);
    }
}
