package com.avada.kino.service;

import com.avada.kino.models.Promotion;
import com.avada.kino.dao.PromotionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService implements DaoService<Promotion>{
    private final PromotionDao dao;

    @Override
    public void save(Promotion promotion) {
        dao.save(promotion);
    }

    @Override
    public List<Promotion> getAll() {
        return dao.getAll();
    }

    @Override
    public Promotion getById(int id) {
        return dao.getById(id);
    }

    @Override
    public void update(Promotion promotion) {
        dao.update(promotion);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}
