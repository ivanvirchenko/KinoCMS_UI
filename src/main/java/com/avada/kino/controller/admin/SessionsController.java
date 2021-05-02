package com.avada.kino.controller.admin;

import com.avada.kino.models.Cinema;
import com.avada.kino.models.Movie;
import com.avada.kino.models.MovieSession;
import com.avada.kino.service.CinemaService;
import com.avada.kino.service.MovieService;
import com.avada.kino.service.MovieSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.time.LocalTime;

import static com.avada.kino.util.UtilConstant.CINEMA_ATTRIBUTE;

@Controller
@RequestMapping("/admin/session")
@RequiredArgsConstructor
public class SessionsController {

    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final MovieSessionService sessionService;

    @GetMapping("/add")
    public String addScheduleForm(@RequestParam("cinema-id") int cinemaId, Model model, HttpSession session) {
        session.setAttribute("cinema-id", cinemaId);

        model.addAttribute("movieSession", new MovieSession());
        model.addAttribute("movies", movieService.getAll());
        return "/admin/session-admin-add";
    }

    @PostMapping("/save")
    public String saveMovieSession(
            @RequestParam("start-time") String time,
            @RequestParam("movie_id") int movieId,
            @RequestParam("hall_id") int hallId,
            MovieSession movieSession,
            HttpSession session
    ) {
        int cinemaId = (int) session.getAttribute("cinema-id");
        Cinema cinema = cinemaService.getById(cinemaId);

        movieSession.setTime(LocalTime.parse(time));
        cinema.getHallsList().stream()
                .filter(h -> h.getId() == hallId)
                .findAny().ifPresent(movieSession::setHall);

        movieSession.setMovie(movieService.getById(movieId));
        cinema.addMovieSession(movieSession);
        cinemaService.update(cinema);
        session.removeAttribute("cinema-id");

        return "redirect:/admin/cinema/" + cinema.getId();
    }

    @GetMapping("/{id}")
    public String update(
            @PathVariable int id
    ) {
        return "/admin/session-admin-concrete";
    }

    @PostMapping("/update")
    public String update(
            MovieSession movieSession,
            HttpSession httpSession,
            @RequestParam("start-time") String time,
            @RequestParam("movie.id") int movieId,
            @RequestParam("hall.id") int hallId
    ) {
        Cinema cinema = (Cinema) httpSession.getAttribute(CINEMA_ATTRIBUTE);
        Movie movie = movieService.getById(movieId);

        movieSession.setCinema(cinema);
        movieSession.setMovie(movie);
        movieSession.setTime(LocalTime.parse(time));
        cinema.getHallsList().stream()
                .filter(hall -> hall.getId() == hallId)
                .findAny()
                .ifPresent(movieSession::setHall);
        sessionService.update(movieSession);

        httpSession.invalidate();
        return "redirect:/admin/cinema/" + cinema.getId();
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam("session-id") int sessionId,
            @RequestParam("cinema-id") int cinemaId
    ) {
        sessionService.deleteById(sessionId);
        return "redirect:/admin/cinema/" + cinemaId;
    }
}
