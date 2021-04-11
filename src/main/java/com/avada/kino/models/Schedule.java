package com.avada.kino.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Data
@Entity
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate localDate;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "movie_sessions")
    private List<MovieSession> sessions;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    public void addSession(MovieSession session) {
        sessions.add(session);
    }


}
