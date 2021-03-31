package com.avada.kino.service;

import com.avada.kino.models.Promotion;
import com.avada.kino.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository repository;

    public List<Promotion> getAll() {
        return repository.getAll();
    }

    public List<Promotion> getEnabled(boolean enabled) {
        return repository.getEnabled(enabled);
    }

    public Promotion getById(int id) {
        return repository.getById(id);
    }

    public void save(Promotion promotion) {
        repository.save(promotion);
    }

    public void update(Promotion promotion) {
        repository.update(promotion);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
