package com.avada.kino.repository;

import com.avada.kino.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("select m from Movie m where :date between m.startDate and m.endDate")
    List<Movie> getCurrent(@Param("date") LocalDate date);

    @Query("select m from Movie m where m.startDate > :date")
    List<Movie> getFuture(@Param("date") LocalDate date);
}
