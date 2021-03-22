package com.avada.kino.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate localDate;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "movie_sessions")
    private List<MovieSession> sessions;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    public Schedule(LocalDate localDate, List<MovieSession> sessions) {
        this.localDate = localDate;
        this.sessions = sessions;
    }
}
