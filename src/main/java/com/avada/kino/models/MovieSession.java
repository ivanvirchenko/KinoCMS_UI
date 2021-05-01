package com.avada.kino.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static javax.persistence.CascadeType.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate date;
    private LocalTime time;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
    @ManyToOne(cascade = {MERGE, REFRESH, DETACH})
    @JoinColumn(name = "hall_id")
    private Hall hall;
}
