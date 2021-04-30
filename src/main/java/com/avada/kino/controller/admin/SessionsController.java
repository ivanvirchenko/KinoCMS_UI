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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String addScheduleForm(@RequestParam("id") int cinemaId, Model model, HttpSession session) {
        Cinema cinema = cinemaService.getById(cinemaId);
        session.setAttribute(CINEMA_ATTRIBUTE, cinema);

        model.addAttribute("movieSession", new MovieSession());
        model.addAttribute("cinema", cinema);
        model.addAttribute("movies", movieService.getAll());
        return "/admin/schedule-admin-add";
    }

    @PostMapping("/save")
    public String saveMovieSession(
            @RequestParam("start-time") String time,
            @RequestParam("movie_id") int movieId,
            @RequestParam("hall_id") int hallId,
            MovieSession movieSession,
            HttpSession session
    ) {
        Cinema cinema = (Cinema) session.getAttribute(CINEMA_ATTRIBUTE);

        movieSession.setTime(LocalTime.parse(time));
        cinema.getHallsList().stream()
                .filter(h -> h.getId() == hallId)
                .findAny().ifPresent(movieSession::setHall);
        movieSession.setMovie(movieService.getById(movieId));

        cinema.addMovieSession(movieSession);
        cinemaService.update(cinema);
        session.invalidate();

        return "redirect:/admin/cinema/" + cinema.getId();
    }

    @GetMapping("/update")
    public String update(
            Model model,
            HttpSession httpSession,
            @RequestParam("session-id") int sessionId,
            @RequestParam("cinema-id") int cinemaId
    ) {
        Cinema cinema = cinemaService.getById(cinemaId);
        httpSession.setAttribute(CINEMA_ATTRIBUTE, cinema);
        MovieSession session = sessionService.getById(sessionId);

        model.addAttribute("movieSession", session);
        model.addAttribute("time", session.getTime().toString());
        model.addAttribute("movies", movieService.getAll());
        model.addAttribute("cinema", cinema);
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
