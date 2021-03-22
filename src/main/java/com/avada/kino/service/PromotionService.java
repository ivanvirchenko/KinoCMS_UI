package com.avada.kino.service;

import com.avada.kino.models.Promotion;
import com.avada.kino.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository repository;

    public List<Promotion> getAll() {
        return repository.getAll();
    }
}
