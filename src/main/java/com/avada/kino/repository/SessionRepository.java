package com.avada.kino.repository;

import com.avada.kino.models.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<MovieSession, Integer> {
    List<MovieSession> findAllByCinemaId(int id);
}
