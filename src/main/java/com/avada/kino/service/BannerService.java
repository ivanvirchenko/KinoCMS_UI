package com.avada.kino.service;

import com.avada.kino.models.Banner;
import com.avada.kino.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;
    private final FilesService filesService;

    public void save(Banner banner) {
        bannerRepository.save(banner);
    }

    public Banner getById(int id) {
        return bannerRepository.getById(id);
    }

    public List<Banner> getAll() {
        return bannerRepository.getAll();
    }

    public void update(Banner banner) {
        bannerRepository.update(banner);
    }

    public void delete(int id) {
        bannerRepository.delete(id);
    }
}
