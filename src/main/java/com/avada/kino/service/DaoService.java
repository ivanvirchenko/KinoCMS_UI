package com.avada.kino.service;

import java.util.List;

public interface DaoService<T>{
    void save(T t);

    List<T> getAll();

    T getById(int id);

    void update(T t);

    void delete(int id);
}
