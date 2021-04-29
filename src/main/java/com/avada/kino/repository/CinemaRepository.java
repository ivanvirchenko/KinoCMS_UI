package com.avada.kino.repository;

import com.avada.kino.models.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {

    @Query("select c From Cinema c left join fetch c.sessions where c.id = :id")
    Cinema findById(@Param("id") int id);

}
