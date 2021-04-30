package com.avada.kino.service;

import com.avada.kino.models.City;
import com.avada.kino.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService implements DaoService<City> {
    private final CityRepository repository;

    @Override
    public void save(City city) {
        repository.save(city);
    }

    @Override
    public List<City> getAll() {
        return repository.findAll();
    }

    @Override
    public City getById(int id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void update(City city) {
        repository.save(city);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

}
