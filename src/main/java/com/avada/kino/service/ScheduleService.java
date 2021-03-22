package com.avada.kino.service;

import com.avada.kino.models.Schedule;
import com.avada.kino.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository repository;

    public List<Schedule> getByCinema(int id) {
        return repository.getByCinema(id);
    }


}
