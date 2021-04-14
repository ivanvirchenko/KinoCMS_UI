package com.avada.kino.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Movie extends BasicEntity{
    private String videoLink;
    @ManyToMany(cascade = {MERGE})
    @JoinTable(
            name = "movies_to_types",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<MovieType> types;

    private LocalDate startDate;

    private LocalDate endDate;

    public void addType(MovieType type) {
        types.add(type);
    }

    public Movie() {
        super();
        if (types == null) {
            this.types = new ArrayList<>();
        }
    }
}
