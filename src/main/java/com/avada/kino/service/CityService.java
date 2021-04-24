package com.avada.kino.service;

import com.avada.kino.dao.CityDao;
import com.avada.kino.models.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService implements DaoService<City> {
    private final CityDao dao;

    @Override
    public void save(City city) {
        dao.save(city);
    }

    @Override
    public List<City> getAll() {
        return dao.getAll();
    }

    @Override
    public City getById(int id) {
        return dao.getById(id);
    }

    @Override
    public void update(City city) {
        dao.update(city);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

}
