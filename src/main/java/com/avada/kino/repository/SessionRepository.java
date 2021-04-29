package com.avada.kino.repository;

import com.avada.kino.models.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<MovieSession, Integer> {
}
