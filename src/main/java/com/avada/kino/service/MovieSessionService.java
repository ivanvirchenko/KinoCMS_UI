package com.avada.kino.service;

import com.avada.kino.models.Cinema;
import com.avada.kino.models.MovieSession;
import com.avada.kino.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class MovieSessionService {
    private final SessionRepository repository;

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public MovieSession getById(int id) {
        return repository.findById(id).orElseThrow();
    }

    public void update(MovieSession session) {
        repository.save(session);
    }

}
