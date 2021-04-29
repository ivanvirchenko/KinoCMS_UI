package com.avada.kino.controller.admin;

import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieType;
import com.avada.kino.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.avada.kino.util.StringsConstant.IMAGE_ERROR;
import static com.avada.kino.util.StringsConstant.MOVIE_TYPES;

@Controller
@RequestMapping("admin/movies")
@RequiredArgsConstructor
public class MovieControllerAdmin {
    private final MovieService movieService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("movie_current", movieService.getCurrent());
        model.addAttribute("movie_future", movieService.getFuture());
        return "/admin/movies-admin";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute("movie", movieService.getById(id));
        model.addAttribute("movie_types", movieService.getAllTypes());
        return "/admin/movies-admin-concrete";
    }

    @GetMapping("/add")
    public String addMovie(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("movie_types", movieService.getAllTypes());
        return "/admin/movies-admin-add";
    }

    @PostMapping("/save")
    public String save(
            @Valid Movie movie,
            BindingResult bindingResult,
            Model model,
            @RequestParam("type_ids") int[] typeIds,
            @RequestParam("logo-image") MultipartFile file,
            @RequestParam("gallery-images") MultipartFile[] files
    ) {
        extractMovieTypes(typeIds, movie);
        saveValidation(file, bindingResult, movie);
        if (bindingResult.hasErrors()) {
            model.addAttribute("movie_types", movieService.getAllTypes());
            return "/admin/movies-admin-add";
        }
        movieService.saveWithFiles(movie, file, files);
        return "redirect:/admin/movies";
    }

    @PostMapping("/update")
    public String update(
            Movie movie,
            @RequestParam("checked_type") int[] typeIds,
            @RequestParam("logo-image") MultipartFile file,
            @RequestParam("gallery-images") MultipartFile[] files
    ) {
        extractMovieTypes(typeIds, movie);
        movieService.updateWithFiles(movie, file, files);
        return "redirect:/admin/movies/" + movie.getId();
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam("movie_id") int movieId
    ) {
        movieService.delete(movieId);
        return "redirect:/admin/movies";
    }

    @PostMapping("/logo/delete")
    public String deleteLogo(@RequestParam("movie_id") int movieId, @RequestParam("logo-image") String imageName) {
        movieService.deleteLogo(movieId, imageName);
        return "redirect:/admin/movies/" + movieId;
    }

    @PostMapping("/gallery/delete")
    public String deleteFromGallery(@RequestParam("movie_id") int movieId, @RequestParam("image-name") String logoName) {
        movieService.deleteFromGallery(movieId, logoName);
        return "redirect:/admin/movies/" + movieId;
    }

    private void saveValidation(MultipartFile file, BindingResult result, Movie movie){
        if (movie.getTypes() == null || movie.getTypes().isEmpty()) {
            FieldError typesError = new FieldError("movie", "types", MOVIE_TYPES);
        }
        if (file.isEmpty()) {
            FieldError logoError = new FieldError("movie", "logo", IMAGE_ERROR);
            result.addError(logoError);
        }
    }

    private void extractMovieTypes(int[] typeIds, Movie movie) {
        List<MovieType> typesDb = movieService.getAllTypes();
        List<MovieType> result = new ArrayList<>();
        for (int type_id : typeIds) {
            for (MovieType movieType : typesDb) {
                if (movieType.getId() == type_id) {
                    result.add(movieType);
                }
            }
        }
        movie.setTypes(result);
    }
}
