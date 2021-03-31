package com.avada.kino.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Embeddable
@NoArgsConstructor
public class MovieSession {
    private LocalTime time;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    public MovieSession(LocalTime time, BigDecimal price, Movie movie, Hall hall) {
        this.time = time;
        this.price = price;
        this.movie = movie;
        this.hall = hall;
    }
}
