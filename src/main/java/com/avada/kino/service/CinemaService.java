package com.avada.kino.service;

import com.avada.kino.dao.CinemaDao;
import com.avada.kino.models.Cinema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaService implements DaoService<Cinema>{

    private final CinemaDao dao;

    @Override
    public void save(Cinema cinema) {
        dao.save(cinema);
    }

    @Override
    public List<Cinema> getAll() {
        return dao.getAll();
    }

    public List<Cinema> getAllWithoutCollections() {
        return dao.getAllWithoutCollections();
    }

    @Override
    public Cinema getById(int id) {
        return dao.getById(id);
    }

    @Override
    public void update(Cinema cinema) {
        dao.update(cinema);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}
