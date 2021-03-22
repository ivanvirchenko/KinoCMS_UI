package com.avada.kino.service;

import com.avada.kino.models.Cinema;
import com.avada.kino.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaService {
    private final CinemaRepository repository;

    public List<Cinema> getAll() {
        return repository.getAll();
    }

    public Cinema getById(int id) {
        return repository.getById(id);
    }
}
