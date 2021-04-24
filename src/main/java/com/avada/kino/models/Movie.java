package com.avada.kino.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
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
    @NotEmpty(message = "Нужно выбрать хотябы один тип")
    private List<MovieType> types = new ArrayList<>();

    @FutureOrPresent(message = "Фильм невозможно показывать в прошлом")
    private LocalDate startDate;

    @Future(message = "Дата не может быть меньше текущей")
    private LocalDate endDate;

    public void addType(MovieType type) {
        if (this.types == null) {
            this.types = new ArrayList<>();
        }
        types.add(type);
    }
}
