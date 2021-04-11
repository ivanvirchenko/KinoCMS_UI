package com.avada.kino.dao;

import java.util.List;

public interface Dao<T> {
    void save(T t);

    List<T> getAll();

    T getById(int id);

    void update(T t);

    void delete(int id);
}
