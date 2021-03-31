package com.avada.kino.service;

import com.avada.kino.models.User;
import com.avada.kino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public void save(User user) {
        repository.save(user);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public User getById(int id) {
        return repository.getById(id);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
