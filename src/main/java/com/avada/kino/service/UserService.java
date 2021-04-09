package com.avada.kino.service;

import com.avada.kino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public int getUsersCount() {
        return repository.getUsersCount();
    }
}
