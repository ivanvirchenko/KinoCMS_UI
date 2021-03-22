package com.avada.kino.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Embeddable
public class MovieSession {
    private LocalTime time;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
}
