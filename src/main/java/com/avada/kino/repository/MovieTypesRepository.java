package com.avada.kino.repository;

import com.avada.kino.models.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTypesRepository extends JpaRepository<MovieType, Integer> {
}
